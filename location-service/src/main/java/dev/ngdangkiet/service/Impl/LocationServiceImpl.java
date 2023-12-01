package dev.ngdangkiet.service.Impl;

import com.google.maps.DistanceMatrixApi;
import com.google.maps.DistanceMatrixApiRequest;
import com.google.maps.GeoApiContext;
import com.google.maps.GeocodingApi;
import com.google.maps.PlacesApi;
import com.google.maps.model.AutocompletePrediction;
import com.google.maps.model.DistanceMatrix;
import com.google.maps.model.GeocodingResult;
import com.google.maps.model.Geometry;
import com.google.maps.model.LatLng;
import com.google.maps.model.TravelMode;
import dev.ngdangkiet.dkmicroservices.location.protobuf.PGetLocationRequest;
import dev.ngdangkiet.dkmicroservices.location.protobuf.PLocationResponse;
import dev.ngdangkiet.dkmicroservices.location.protobuf.PLocationsResponse;
import dev.ngdangkiet.mapper.PLocationMapper;
import dev.ngdangkiet.service.LocationService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service
@Slf4j
public class LocationServiceImpl implements LocationService {

    private static final String KEY = System.getenv("GOOGLE_API_KEY");

    private static final GeoApiContext.Builder placeBuilder = new GeoApiContext.Builder().apiKey(KEY);

    private static final GeoApiContext placeContext = placeBuilder.build();

    private static final GeoApiContext.Builder builder = new GeoApiContext.Builder().apiKey(KEY).disableRetries();

    private static final GeoApiContext context = builder.build();

    private static final PLocationMapper pLocationMapper = PLocationMapper.INSTANCE;

    @Override
    public PLocationsResponse getSameNameLocation(String address) {
        return getSameNameLocation(address, "vi");
    }

    @Override
    public PLocationResponse calculateDistanceFrom2Point(PGetLocationRequest request) {
        LatLng latLngFrom = getLatLng(getCoordinate(request.getFromLocation()));
        LatLng latLngTo = getLatLng(getCoordinate(request.getToLocation()));
        PLocationResponse response = pLocationMapper.toDomain(request);
        if (Objects.nonNull(latLngFrom) && Objects.nonNull(latLngTo)) {
            return response
                    .toBuilder()
                    .setDistance(calculateDistanceFrom2Point(latLngFrom, latLngTo, request.getEnv()))
                    .build();
        } else {
            log.warn("Can't calculate distance from 2 points for request: {}", request);
            return response.toBuilder().setDistance(0L).build();
        }
    }

    private PLocationsResponse getSameNameLocation(String address, String language) {
        if (language == null || language.length() == 0) return getSameNameLocation(address);
        List<String> rs = new ArrayList<>();
        try {
            AutocompletePrediction[] places = PlacesApi.queryAutocomplete(placeContext, address).language(language).await();
            for (AutocompletePrediction item : places) {
                rs.add(item.description);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            log.error("An ERROR in function getSameNameLocation : " + ex.getMessage(), ex);
        }
        return PLocationsResponse.newBuilder().addAllAddresses(rs).build();
    }

    public List<Double> getCoordinate(String address) {
        List<Double> coordinate = new ArrayList<>();
        try {
            GeocodingResult[] results = GeocodingApi.geocode(context, address).await();
            if (results != null && results.length > 0) {
                Geometry geometry = results[0].geometry;
                coordinate.add(geometry.location.lat);
                coordinate.add(geometry.location.lng);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            log.error("An ERROR in function getCoordinate : " + ex.getMessage(), ex);
        }
        return coordinate;
    }

    public String getLocation(Double longitude, Double latitude) throws Exception {
        LatLng latLng = new LatLng(latitude, longitude);
        GeocodingResult[] results = GeocodingApi.reverseGeocode(context, latLng).await();
        return results[0].formattedAddress;
    }

    public double calculateDistanceFrom2Point(LatLng p1, LatLng p2, String env) {
        double distApart = 0;
        long distance = 0L;
        if (StringUtils.isNotEmpty(env) && env.equals("UAT")) {
            return getHaversineDistanceInKmTo(p1.lat, p1.lng, p2.lat, p2.lng);
        }

        DistanceMatrixApiRequest req = DistanceMatrixApi.newRequest(context);
        try {
            DistanceMatrix result = req.origins(p1)
                    .destinations(p2)
                    .mode(TravelMode.DRIVING)
                    // .avoid(DirectionsApi.RouteRestriction.TOLLS)
                    .language("en-US")
                    .await();
            distance = result.rows[0].elements[0].distance.inMeters;
            distApart = distance / 1000.0;
        } catch (Exception e) {
            System.err.printf("calculateDistanceFrom2Point for (%s, %s) -> %s%n", p1, p2, e.toString());
        }

        return distApart;
    }

    public double getHaversineDistanceInKmTo(double lat1, double lon1, double lat2, double lon2) {
        final int EARTH_RADIUS_IN_KM = 6371;
        final int TWICE_EARTH_RADIUS_IN_KM = 2 * EARTH_RADIUS_IN_KM;

        double latitudeInRads = Math.toRadians(lat1);
        double longitudeInRads = Math.toRadians(lon1);
        // Cartesian coordinates, normalized for a sphere of diameter 1.0
        double cartesianX = 0.5 * Math.cos(latitudeInRads) * Math.sin(longitudeInRads);
        double cartesianY = 0.5 * Math.cos(latitudeInRads) * Math.cos(longitudeInRads);
        double cartesianZ = 0.5 * Math.sin(latitudeInRads);

        double otherLatitudeInRads = Math.toRadians(lat2);
        double otherLongitudeInRads = Math.toRadians(lon2);
        // Cartesian coordinates, normalized for a sphere of diameter 1.0
        double otherCartesianX = 0.5 * Math.cos(otherLatitudeInRads) * Math.sin(otherLongitudeInRads);
        double otherCartesianY = 0.5 * Math.cos(otherLatitudeInRads) * Math.cos(otherLongitudeInRads);
        double otherCartesianZ = 0.5 * Math.sin(otherLatitudeInRads);

        // TODO cache the part above
        double dX = cartesianX - otherCartesianX;
        double dY = cartesianY - otherCartesianY;
        double dZ = cartesianZ - otherCartesianZ;
        double r = Math.sqrt((dX * dX) + (dY * dY) + (dZ * dZ));
        return TWICE_EARTH_RADIUS_IN_KM * Math.asin(r);
    }

    private LatLng getLatLng(List<Double> coordinate) {
        return (coordinate != null && coordinate.size() == 2) ? new LatLng(coordinate.get(0), coordinate.get(1)) : null;
    }


}

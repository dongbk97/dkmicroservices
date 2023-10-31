package dev.ngdangkiet.mapper;

import org.springframework.util.CollectionUtils;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author ngdangkiet
 * @since 10/31/2023
 */

public interface ProtobufMapper<D, P> {

    D toDomain(P protobuf);

    P toProtobuf(D domain);

    default List<D> toDomains(List<P> protobufs) {
        return CollectionUtils.isEmpty(protobufs)
                ? Collections.emptyList()
                : protobufs.stream().map(this::toDomain).collect(Collectors.toList());
    }

    default List<P> toProtobufs(List<D> domains) {
        return CollectionUtils.isEmpty(domains)
                ? Collections.emptyList()
                : domains.stream().map(this::toProtobuf).collect(Collectors.toList());
    }
}

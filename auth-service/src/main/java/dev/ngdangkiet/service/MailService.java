package dev.ngdangkiet.service;

import com.google.protobuf.StringValue;
import dev.ngdangkiet.dkmicroservices.common.protobuf.EmptyResponse;

/**
 * @author ngdangkiet
 * @since 12/20/2023
 */

public interface MailService {

    EmptyResponse sendMailWithOtp(StringValue mailTo);
}

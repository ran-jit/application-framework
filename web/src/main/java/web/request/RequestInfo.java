package web.request;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import web.auth.AuthenticationInfo;

/**
 * author: Ranjith Manickam @ 28 Jan' 2019.
 */
@Getter
@Setter
@Builder
@ToString
public class RequestInfo extends AbstractRequest {
    private static final long serialVersionUID = -3965961885659864153L;

    private String requestId;
    private AuthenticationInfo authenticationInfo;
}

package web.model;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * author: Ranjith Manickam @ 28 Jan' 2019.
 */
@Getter
@Setter
@Builder
@ToString
public class RequestInfo extends AbstractEntry {
    private static final long serialVersionUID = -3965961885659864153L;

    private String requestId;
    private Object authenticationInfo;
}

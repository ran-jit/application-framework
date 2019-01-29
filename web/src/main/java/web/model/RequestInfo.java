package web.model;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;

/**
 * author: Ranjith Manickam @ 28 Jan' 2019.
 */
@Getter
@Setter
@Builder
@ToString
public class RequestInfo implements Serializable {
    private static final long serialVersionUID = -3965961885659864153L;

    private String requestId;
    private Object authenticationInfo;
}

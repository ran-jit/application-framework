package web.status;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.io.Serializable;

@Getter
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class StatusCode implements Serializable {
    private static final long serialVersionUID = 9153289558241080120L;

    private String code;
    private String message;
}

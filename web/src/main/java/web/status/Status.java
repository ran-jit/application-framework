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
public class Status implements Serializable {
    private static final long serialVersionUID = 7333060997696991725L;

    private Type type;
    private Boolean success;
    private StatusCode statusCode;

    public enum Type {
        ERROR, SUCCESS;
    }
}

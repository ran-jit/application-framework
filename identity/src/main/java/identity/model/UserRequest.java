package identity.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import web.model.AbstractRequest;

@Getter
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class UserRequest extends AbstractRequest {
    private static final long serialVersionUID = 2528765772624068184L;

    private Long userid;
    private String username;
    private String password;
}

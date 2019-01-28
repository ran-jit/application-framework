package data.sql.config;

import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

import java.io.Serializable;

/**
 * author: Ranjith Manickam @ 28 Jan' 2019.
 */
@Getter
@Builder
@ToString
@EqualsAndHashCode(of = {"alias"})
public class DatabaseConfig implements Serializable {
    public static final String DEFAULT_ALIAS = "default";
    private static final long serialVersionUID = -9204925166261863791L;

    @Builder.Default
    private String alias = DEFAULT_ALIAS;
    private String driverClass;
    private String connectionUrl;
    private String username;
    private String password;
    private Boolean showSQL;
}

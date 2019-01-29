package identity.model;

import com.google.common.collect.Sets;
import lombok.Builder;
import lombok.Getter;

import java.io.Serializable;
import java.util.Set;

/**
 * author: Ranjith Manickam @ 28 Jan' 2019.
 */
@Getter
@Builder
public class FilterURIs implements Serializable {
    private static final long serialVersionUID = -6811914990371963127L;

    private Set<String> byPassURIs;

    public Set<String> getByPassURIs() {
        return this.byPassURIs == null ? Sets.newHashSet() : this.byPassURIs;
    }
}

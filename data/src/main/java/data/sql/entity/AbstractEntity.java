package data.sql.entity;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Version;
import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * author: Ranjith Manickam @ 28 Jan' 2019.
 */
@Getter
@Setter
@MappedSuperclass
@EqualsAndHashCode(of = {"id"})
public abstract class AbstractEntity<ID extends Serializable> implements Serializable {
    private static final long serialVersionUID = -6700815066911523333L;

    @Id
    @Column(name = "id", updatable = false, insertable = false)
    protected ID id;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "created_on", nullable = false, updatable = false)
    protected LocalDateTime createdOn;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "last_modified_on", nullable = false)
    protected LocalDateTime lastModifiedOn;

    @Version
    @Column(name = "version")
    protected Long version = 0L;

    @PrePersist
    protected void onCreate() {
        this.lastModifiedOn = createdOn = ((createdOn == null) ? LocalDateTime.now() : createdOn);
    }

    @PreUpdate
    protected void onUpdate() {
        this.lastModifiedOn = LocalDateTime.now();
    }
}

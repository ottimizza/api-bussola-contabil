package br.com.ottimizza.dashboard.models;

import br.com.ottimizza.dashboard.models.users.User;
import java.io.Serializable;
import java.math.BigInteger;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@NoArgsConstructor @AllArgsConstructor
@Table(name = "authorized_tokens")
public class Session implements Serializable {

    @Id
    @SequenceGenerator(name = "authorized_tokens_sequence", sequenceName = "authorized_tokens_id_sequence", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "authorized_tokens_sequence")
    @Getter @Setter
    @Column(name = "id", nullable = false)
    private BigInteger id;

    @Getter @Setter
    @Column(name = "token", nullable = false)
    private String token;

    @Getter @Setter
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "created_at")
    private Date createdAt;

    @Getter @Setter
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "updated_at")
    private Date updatedAt;

    @OneToOne
    @Getter @Setter
    @JoinColumn(name = "fk_users_id", nullable = false)
    private User user;

    @PreUpdate
    @PrePersist
    public void updateTimestamps() {
        updatedAt = new Date();
        if (createdAt == null) {
            createdAt = new Date();
        }
    }

}

package com.jefrido.cagnotte.domain.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import lombok.Getter;
import lombok.Setter;

import java.util.LinkedHashSet;
import java.util.Objects;
import java.util.Set;

@Getter
@Setter
@Entity
@Table(name = "clients", schema = "public", uniqueConstraints = {
    @UniqueConstraint(name = "clients_email_key", columnNames = {"email"})
})
public class Client {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "clients_id_gen")
    @SequenceGenerator(name = "clients_id_gen", sequenceName = "client_sequence", allocationSize = 1)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "name", nullable = false, length = 100)
    private String name;

    @Column(name = "email", nullable = false, length = 100)
    private String email;

    @OneToOne(mappedBy = "client")
    private Cagnotte cagnotte;

    @OneToMany(mappedBy = "client")
    private Set<Transaction> transactions = new LinkedHashSet<>();

    @Override
    public final boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Client client)) {
            return false;
        }

        return Objects.equals(id, client.id) && Objects.equals(name, client.name) && Objects.equals(email, client.email) &&
            Objects.equals(cagnotte, client.cagnotte) && Objects.equals(transactions, client.transactions);
    }

    @Override
    public int hashCode() {
        int result = Objects.hashCode(id);
        result = 31 * result + Objects.hashCode(name);
        result = 31 * result + Objects.hashCode(email);
        result = 31 * result + Objects.hashCode(cagnotte);
        result = 31 * result + Objects.hashCode(transactions);
        return result;
    }
}

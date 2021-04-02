package org.greenspark404.kanbanboard.data.model;

import javax.persistence.*;
import java.util.Locale;

@Entity
@Table(name = "user_settings")
public class UserSettings extends BaseEntity {

    @OneToOne
    @MapsId
    @JoinColumn(name = "user_id")
    private User user;

    private Locale preferredLocale;

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Locale getPreferredLocale() {
        return preferredLocale;
    }

    public void setPreferredLocale(Locale preferredLocale) {
        this.preferredLocale = preferredLocale;
    }
}

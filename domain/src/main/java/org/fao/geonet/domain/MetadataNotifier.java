package org.fao.geonet.domain;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Nullable;
import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.fao.geonet.repository.MetadataNoficationRepository;

/**
 * An entity representing a service that desires to be notified when a metadata is modified.
 * 
 * @author Jesse
 */
@Entity
@Access(AccessType.PROPERTY)
@Table(name = "metadatanotifiers")
public class MetadataNotifier {
    private int _id;
    private String _name;
    private String _url;
    private char _enabled = Constants.YN_DISABLED;
    private String _username;
    private char[] _password;
    private List<MetadataNotification> _notifications = new ArrayList<MetadataNotification>();

    /**
     * Get the id of this notifier. This is a generated value and as such new instances should not have this set as it will simply be
     * ignored and could result in reduced performance.
     * 
     * @return the id of this notifier.
     */
    @Id
    @GeneratedValue
    public int getId() {
        return _id;
    }

    /**
     * Set the id of this notifier. This is a generated value and as such new instances should not have this set as it will simply be
     * ignored and could result in reduced performance.
     * 
     * @param id the id of this notifier
     */
    public void setId(int id) {
        this._id = id;
    }

    /**
     * Get the "name" (human readable description) of the notifier.
     * 
     * @return the "name" (human readable description) of the notifier.
     */
    @Column(nullable = false, length = 32)
    public String getName() {
        return _name;
    }

    /**
     * Set the "name" (human readable description) of the notifier.
     * 
     * @param name the name of the notifier
     */
    public void setName(String name) {
        this._name = name;
    }

    /**
     * Get the URL to use to notify the notifier. This is a required non-null property.
     * 
     * @return the URL to use to notify the notifier.
     */
    @Column(nullable = false)
    public String getUrl() {
        return _url;
    }

    /**
     * Set the URL to use to notify the notifier. This is a required non-null property.
     * 
     * @param url the URL to use to notify the notifier.
     */
    public void setUrl(String url) {
        this._url = url;
    }

    /**
     * For backwards compatibility we need the enabled column to be either 'n' or 'y'. This is a workaround to allow this until future
     * versions of JPA that allow different ways of controlling how types are mapped to the database.
     */
    @Column(name = "enabled", length = 1, nullable = false)
    protected char getEnabled_JPAWorkaround() {
        return _enabled;
    }

    /**
     * Set the enabled column value as either Constants.YN_ENABLED or Constants.YN_DISABLED
     * 
     * @param enabled either Constants.YN_ENABLED for true or Constants.YN_DISABLED for false
     */
    protected void setEnabled_JPAWorkaround(char enabled) {
        this._enabled = enabled;
    }

    /**
     * Return true if the notifier is enabled and should be notified of changes.
     * 
     * @return true if the notifier is enabled and should be notified of changes.
     */
    @Transient
    public boolean isEnabled() {
        return Constants.toBoolean_fromYNChar(getEnabled_JPAWorkaround());
    }

    /**
     * Set true if the notifier is enabled and should be notified of changes.
     * 
     * @param enabled true if the notifier is enabled and should be notified of changes.
     */
    public void setEnabled(boolean enabled) {
        setEnabled_JPAWorkaround(Constants.toYN_EnabledChar(enabled));
    }

    /**
     * Get the username to use as credentials when notifying the notifier. This may be null.
     * 
     * @return the username to use as credentials when notifying the notifier. This may be null.
     */
    @Column(length = 32)
    @Nullable
    public String getUsername() {
        return _username;
    }

    /**
     * Set the username to use as credentials when notifying the notifier. This may be null.
     * 
     * @param username the username to use as credentials when notifying the notifier. This may be null.
     */
    public void setUsername(@Nullable String username) {
        this._username = username;
    }

    /**
     * Get the password to use as credentials when notifying the notifier. This may be null.
     * 
     * @return the password to use as credentials when notifying the notifier. This may be null.
     */
    @Nullable
    public char[] getPassword() {
        return _password;
    }

    /**
     * Set the password to use as credentials when notifying the notifier. This may be null.
     * 
     * @param password the password to use as credentials when notifying the notifier. This may be null.
     */
    public void setPassword(@Nullable char[] password) {
        this._password = password;
    }

    /**
     * Set the password to use as credentials when notifying the notifier. This may be null.
     * 
     * @param password the password to use as credentials when notifying the notifier. This may be null.
     */
    public void setPassword(@Nullable String password) {
        this._password = password.toCharArray();
    }

    /**
     * Get the lazily loaded list of all the notifications for this notifier.
     * <p>
     * For performance on might use the {@link MetadataNoficationRepository} to efficiently look up just the notifications needed.
     * </p>
     * 
     * @return the lazily loaded list of all the notifications for this notifier.
     */
    @JoinColumn(name = "notifierId")
    @OneToMany(fetch = FetchType.LAZY)
    public List<MetadataNotification> getNotifications() {
        return _notifications;
    }

    /**
     * Set the notifications for this metadata notifier
     * 
     * @param notifications the notifications
     */
    protected void setNotifications(List<MetadataNotification> notifications) {
        this._notifications = notifications;
    }

}

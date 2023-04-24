/*
 * This file is generated by jOOQ.
 */
package domain.tables.pojos;


import jakarta.validation.constraints.Size;

import java.beans.ConstructorProperties;
import java.io.Serializable;
import java.time.LocalDateTime;

import javax.annotation.processing.Generated;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;


/**
 * This class is generated by jOOQ.
 */
@Generated(
    value = {
        "https://www.jooq.org",
        "jOOQ version:3.18.3"
    },
    comments = "This class is generated by jOOQ"
)
@SuppressWarnings({ "all", "unchecked", "rawtypes" })
public class Link implements Serializable {

    private static final long serialVersionUID = 1L;

    private String link;
    private LocalDateTime lastActivityTime;
    private LocalDateTime lastCheckTime;

    public Link() {}

    public Link(Link value) {
        this.link = value.link;
        this.lastActivityTime = value.lastActivityTime;
        this.lastCheckTime = value.lastCheckTime;
    }

    @ConstructorProperties({ "link", "lastActivityTime", "lastCheckTime" })
    public Link(
        @NotNull String link,
        @NotNull LocalDateTime lastActivityTime,
        @Nullable LocalDateTime lastCheckTime
    ) {
        this.link = link;
        this.lastActivityTime = lastActivityTime;
        this.lastCheckTime = lastCheckTime;
    }

    /**
     * Getter for <code>public.link.link</code>.
     */
    @jakarta.validation.constraints.NotNull
    @Size(max = 200)
    @NotNull
    public String getLink() {
        return this.link;
    }

    /**
     * Setter for <code>public.link.link</code>.
     */
    public void setLink(@NotNull String link) {
        this.link = link;
    }

    /**
     * Getter for <code>public.link.last_activity_time</code>.
     */
    @jakarta.validation.constraints.NotNull
    @NotNull
    public LocalDateTime getLastActivityTime() {
        return this.lastActivityTime;
    }

    /**
     * Setter for <code>public.link.last_activity_time</code>.
     */
    public void setLastActivityTime(@NotNull LocalDateTime lastActivityTime) {
        this.lastActivityTime = lastActivityTime;
    }

    /**
     * Getter for <code>public.link.last_check_time</code>.
     */
    @Nullable
    public LocalDateTime getLastCheckTime() {
        return this.lastCheckTime;
    }

    /**
     * Setter for <code>public.link.last_check_time</code>.
     */
    public void setLastCheckTime(@Nullable LocalDateTime lastCheckTime) {
        this.lastCheckTime = lastCheckTime;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        final Link other = (Link) obj;
        if (this.link == null) {
            if (other.link != null)
                return false;
        }
        else if (!this.link.equals(other.link))
            return false;
        if (this.lastActivityTime == null) {
            if (other.lastActivityTime != null)
                return false;
        }
        else if (!this.lastActivityTime.equals(other.lastActivityTime))
            return false;
        if (this.lastCheckTime == null) {
            if (other.lastCheckTime != null)
                return false;
        }
        else if (!this.lastCheckTime.equals(other.lastCheckTime))
            return false;
        return true;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((this.link == null) ? 0 : this.link.hashCode());
        result = prime * result + ((this.lastActivityTime == null) ? 0 : this.lastActivityTime.hashCode());
        result = prime * result + ((this.lastCheckTime == null) ? 0 : this.lastCheckTime.hashCode());
        return result;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("Link (");

        sb.append(link);
        sb.append(", ").append(lastActivityTime);
        sb.append(", ").append(lastCheckTime);

        sb.append(")");
        return sb.toString();
    }
}

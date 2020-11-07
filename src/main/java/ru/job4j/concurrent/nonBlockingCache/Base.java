package ru.job4j.concurrent.nonBlockingCache;

import java.util.Objects;

/**
 * Base
 *
 * @author Vladislav Nechaev
 * @since 04.11.2020
 */

public class Base {

    private final int id;
    private int version;
    private String name;

    public Base(int id) {
        this.id = id;
        version = 0;
    }

    public int getId() {
        return id;
    }

    public int getVersion() {
        return version;
    }

    public void setVersion(int version) {
        this.version = version;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Base base = (Base) o;
        return id == base.id &&
                version == base.version &&
                Objects.equals(name, base.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, version, name);
    }
}

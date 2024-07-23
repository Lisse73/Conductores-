package application;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.beans.property.IntegerProperty;

public class DriverPoints {
    private final StringProperty driverRef;
    private final IntegerProperty totalPoints;

    public DriverPoints(String driverRef, int totalPoints) {
        this.driverRef = new SimpleStringProperty(driverRef);
        this.totalPoints = new SimpleIntegerProperty(totalPoints);
    }

    public String getDriverRef() {
        return driverRef.get();
    }

    public void setDriverRef(String driverRef) {
        this.driverRef.set(driverRef);
    }

    public StringProperty driverRefProperty() {
        return driverRef;
    }

    public int getTotalPoints() {
        return totalPoints.get();
    }

    public void setTotalPoints(int totalPoints) {
        this.totalPoints.set(totalPoints);
    }

    public IntegerProperty totalPointsProperty() {
        return totalPoints;
    }
}

module com.thunv25.doanktpm {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.base;
    requires java.sql;
    requires org.apache.commons.lang3;
    requires jdk.unsupported;

    opens com.thunv25.doanktpm to javafx.fxml;
    exports com.thunv25.doanktpm;
    exports com.thunv25.pojo;
    exports com.thunv25.utils;
}

package xcelite.model;

import compat.com.ebay.xcelite_104.annotations.Compat_Column;

public class BeanWriterTestsBean {

    @Compat_Column(name = "LONG_STRING")
    private String longString;

    public String getLongString() {
        return longString;
    }

    public void setLongString(String longString) {
        this.longString = longString;
    }
}

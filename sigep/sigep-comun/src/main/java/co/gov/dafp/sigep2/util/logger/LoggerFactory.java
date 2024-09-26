package co.gov.dafp.sigep2.util.logger;

class LoggerFactory {

    private LoggerFactory() {
        super();
    }

    public static org.slf4j.Logger getLogger(Class<?> clazz) {
        return org.slf4j.LoggerFactory.getLogger(clazz);
    }

    public static org.slf4j.Logger getLogger(String nameClazz) {
        return org.slf4j.LoggerFactory.getLogger(nameClazz);
    }

}

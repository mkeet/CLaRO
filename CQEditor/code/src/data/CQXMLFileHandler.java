package data;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;

public class CQXMLFileHandler {

    public static ObjectFactory xmlObjectCreator = new ObjectFactory();
    private static JAXBContext jaxbContext;
    private static Unmarshaller jaxbUnmarshaller;
    private static Marshaller jaxbMarshaller;

    public static void Init() throws Exception {
        jaxbContext = JAXBContext.newInstance(RecordList.class);
        jaxbUnmarshaller = jaxbContext.createUnmarshaller();
        jaxbMarshaller = jaxbContext.createMarshaller();
        jaxbMarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
    }

    public static RecordList read(String filename) throws Exception {
        InputStream inStream = new FileInputStream(filename);
        return read(inStream);
    }

    public static RecordList read(File file) throws Exception {
        InputStream inStream = new FileInputStream(file);
        return read(inStream);
    }

    public static RecordList read(InputStream fileStream) throws Exception {
        if (jaxbUnmarshaller == null) {
            Init();
        }
        RecordList recordList = (RecordList) jaxbUnmarshaller.unmarshal(fileStream);
        return recordList;
    }

    public static void write(String filename, RecordList records) throws Exception {
        File file = new File(filename);
        write(file, records);
    }

    public static void write(File file, RecordList records) throws Exception {
        if (jaxbMarshaller == null) {
            Init();
        }
        jaxbMarshaller.marshal(records, file);
    }
}

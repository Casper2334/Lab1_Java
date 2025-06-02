package Library;

import com.fasterxml.jackson.databind.ObjectMapper;


import java.io.File;
import java.io.IOException;

public class JsonUtil {
    private static final ObjectMapper mapper =  new ObjectMapper();;

    public static void saveLibraryToFile(Library library, String fileName) throws IOException {
        mapper.writeValue(new File(fileName), library);
    }

    public static Library loadLibraryFromFile(String fileName) throws IOException {
        return mapper.readValue(new File(fileName), Library.class);
    }
}
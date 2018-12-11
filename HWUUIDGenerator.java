import java.io.BufferedReader;
import java.io.File;
import java.io.FileWriter;
import java.io.InputStreamReader;
import java.util.UUID;

public class HWUUIDGenerator {
    private String uuid;

    public final String onGenerateUUID() {
        String result = "";
        try {
            File file = File.createTempFile("realhowto", ".vbs");
            file.deleteOnExit();
            FileWriter fw = new FileWriter(file);

            String vbs = "Set objFSO = CreateObject(\"Scripting.FileSystemObject\")\nSet colDrives = objFSO.Drives\nSet objDrive = colDrives.item(\"C:\")\nWscript.Echo objDrive.SerialNumber";

            fw.write(vbs);
            fw.close();
            Process p = Runtime.getRuntime().exec("cscript //NoLogo " + file.getPath());
            BufferedReader input = new BufferedReader(new InputStreamReader(p.getInputStream()));
            String line;
            while ((line = input.readLine()) != null) result += line;
            input.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        UUID u2 = UUID.nameUUIDFromBytes(result.getBytes());
        this.uuid = u2.toString();
        System.out.println("Successfully generated UUID:" + this.uuid);
        return this.uuid;
    }

    public String getUUID() {
        return this.uuid;
    }
}
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoDatabase;

public class App {

    public static void main(String[] args) {

        String uri = "mongodb+srv://Lucas:12345678910@cluster0.u8ebhwv.mongodb.net/?appName=Cluster0";

        try (MongoClient mongoClient = MongoClients.create(uri)) {

            MongoDatabase database = mongoClient.getDatabase("teste");

            System.out.println("Conectado ao MongoDB com sucesso!");

        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
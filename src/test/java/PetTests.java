import com.fasterxml.jackson.core.JsonProcessingException;
import models.Category;
import models.Pet;
import models.Tag;
import org.junit.Test;
import service.PetService;

import java.io.IOException;


public class PetTests {

    // Note *
    // assuming can reuse ID's,if I was not I would have used random or fetched the last and incremented.
    //Also created new data for each to run in parallel if needs been in future

    @Test()
    public void CreatePet() throws JsonProcessingException {
        Pet dog = CreatePetData("albinoDogs", "https://www.dogs.com", "#Dog", 1, "Ted", "available");

//      2. Verify The Pet was created with correct data.
//         Here I pass in the pet object, create it and assert it againt the json of the newly created
//         Pet returned in the response
        PetService.createPet(dog);
    }

    @Test()
    public void EditPetParams() throws IOException {
        Pet cat = CreatePetData("blackCats", "https://www.cats.com", "#Cat", 100, "john", "unavailable");
        cat.setName("newCatName");
        cat.setStatus("unavailable");

//         3. Update this Pet_name, Verify update and return record
//        Update the cat with new data and then fetch the cat and assert the new data is in the response
//        Wasn't sure which update method you wanted so covered both
//        This api returns with just those that I've changed and the rest null so it fails.
        PetService.updatePet(cat.getID(), cat.getName(),cat.getStatus());
        PetService.getPet(cat,false);

    }

    @Test()
    public void EditPet() throws IOException {
        Pet pig = CreatePetData("fatPigs", "https://www.pigs.com", "#Pig", 3, "burt", "unavailable");
        PetService.createPet(pig);
        pig.setName("changedName");
        pig.setStatus("unavailable");
        pig.getCategory().setId(2);
        pig.getCategory().setName("fattestPigs");

 //         3. Update this Pet_name, Verify update and return record
//        Update the cat with new data and then fetch the cat and assert the new data is in the response
//        Wasn't sure which update method you wanted so covered both
        PetService.updatePet(pig);
    }

    @Test()
    public void DeletePet() throws IOException {

//   4. Delete the Pet and demonstrate pet now deleted.
//      So here I delete the pet and then call the getPet method and the response code should be 404 as it's deleted

        Pet pig = CreatePetData("fatPigs", "https://www.pigs.com", "#Pig", 4, "piggo", "unavailable");
        PetService.createPet(pig);
        PetService.deletePet(pig.getID());
        PetService.getPet(pig,true);
    }


    private Pet CreatePetData(String cat, String url, String tag, int i, String ted, String available) {
        Category category = new Category(1, cat);
        String[] urls = new String[]{url};
        Tag[] tags = new Tag[]{new Tag(1, tag)};
        return new Pet(i, category, ted, urls, tags, available);
    }

}


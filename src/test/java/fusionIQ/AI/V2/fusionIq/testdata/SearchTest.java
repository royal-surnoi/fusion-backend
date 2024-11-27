//package fusionIQ.AI.V2.fusionIq.testdata;
//
//import fusionIQ.AI.V2.fusionIq.data.Search;
//import fusionIQ.AI.V2.fusionIq.data.User;
//import org.junit.jupiter.api.Test;
//import static org.assertj.core.api.Assertions.assertThat;
//
//class SearchTest {
//
//    @Test
//    void testSearchEntityDefaultConstructor() {
//        // Test default constructor
//        Search search = new Search();
//        assertThat(search).isNotNull();
//        assertThat(search.getId()).isNull();
//        assertThat(search.getSearchContent()).isNull();
//        assertThat(search.getCourseContent()).isNull();
//        assertThat(search.getUser()).isNull();
//    }
//
//    @Test
//    void testSearchEntityParameterizedConstructor() {
//        // Test parameterized constructor
//        User user = new User();
//        user.setId(1L); // Assume User has an ID field for this example
//        Search search = new Search(1L, "Test Search", "Test Course Content", user);
//
//        assertThat(search).isNotNull();
//        assertThat(search.getId()).isEqualTo(1L);
//        assertThat(search.getSearchContent()).isEqualTo("Test Search");
//        assertThat(search.getCourseContent()).isEqualTo("Test Course Content");
//        assertThat(search.getUser()).isEqualTo(user);
//    }
//
//    @Test
//    void testGettersAndSetters() {
//        // Test getters and setters
//        User user = new User();
//        user.setId(1L); // Assume User has an ID field for this example
//
//        Search search = new Search();
//        search.setId(1L);
//        search.setSearchContent("Test Search");
//        search.setCourseContent("Test Course Content");
//        search.setUser(user);
//
//        assertThat(search.getId()).isEqualTo(1L);
//        assertThat(search.getSearchContent()).isEqualTo("Test Search");
//        assertThat(search.getCourseContent()).isEqualTo("Test Course Content");
//        assertThat(search.getUser()).isEqualTo(user);
//    }
//
//    @Test
//    void testToString() {
//        // Test toString method
//        User user = new User();
//        user.setId(1L); // Assume User has an ID field for this example
//
//        Search search = new Search(1L, "Test Search", "Test Course Content", user);
//
//        String expectedToString = "Search{id=1, searchContent='Test Search', courseContent='Test Course Content', user=" + user + "}";
//        assertThat(search.toString()).isEqualTo(expectedToString);
//    }
//}
//

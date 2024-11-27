//package fusionIQ.AI.V2.fusionIq.testdata;
//
//import fusionIQ.AI.V2.fusionIq.data.GroupMessages;
//import fusionIQ.AI.V2.fusionIq.data.Message;
//import fusionIQ.AI.V2.fusionIq.data.User;
//import org.junit.jupiter.api.Test;
//
//import java.time.LocalDateTime;
//
//import static org.assertj.core.api.Assertions.assertThat;
//
//class MessageTests {
//
//    @Test
//    void testMessageCreation() {
//        // Set up a sample user and group message
//        User sender = new User();
//        sender.setId(1L);
//
//        User receiver = new User();
//        receiver.setId(2L);
//
//        GroupMessages groupMessages = new GroupMessages();
//        groupMessages.setId(1L);
//
//        // Create a message
//        Message message = new Message();
//        message.setSender(sender);
//        message.setReceiver(receiver);
//        message.setGroupMessages(groupMessages);
//        message.setMessageContent("Hello, this is a test message.");
//        message.setSent(true);
//        message.setRead(false);
//        message.setFileUrl("http://example.com/file");
//        message.setSenderDeleted(false); // Updated method name
//        message.setReceiverDeleted(false); // Updated method name
//
//        // Check if all fields are set correctly
//        assertThat(message.getSender()).isEqualTo(sender);
//        assertThat(message.getReceiver()).isEqualTo(receiver);
//        assertThat(message.getGroupMessages()).isEqualTo(groupMessages);
//        assertThat(message.getMessageContent()).isEqualTo("Hello, this is a test message.");
//        assertThat(message.isSent()).isTrue();
//        assertThat(message.isRead()).isFalse();
//        assertThat(message.getFileUrl()).isEqualTo("http://example.com/file");
//        assertThat(message.isSenderDeleted()).isFalse(); // Updated method name
//        assertThat(message.isReceiverDeleted()).isFalse(); // Updated method name
//        assertThat(message.getCreatedAt()).isNotNull();
//    }
//
//    @Test
//    void testMessageGettersAndSetters() {
//        // Create a new message
//        Message message = new Message();
//        message.setId(1L);
//        message.setMessageContent("Test content");
//        message.setSent(true);
//        message.setRead(false);
//        message.setFileUrl("http://example.com/testfile");
//        LocalDateTime now = LocalDateTime.now();
//        message.setCreatedAt(now);
//        message.setSenderDeleted(true); // Updated method name
//        message.setReceiverDeleted(true); // Updated method name
//
//        // Validate the getters
//        assertThat(message.getId()).isEqualTo(1L);
//        assertThat(message.getMessageContent()).isEqualTo("Test content");
//        assertThat(message.isSent()).isTrue();
//        assertThat(message.isRead()).isFalse();
//        assertThat(message.getFileUrl()).isEqualTo("http://example.com/testfile");
//        assertThat(message.getCreatedAt()).isEqualTo(now);
//        assertThat(message.isSenderDeleted()).isTrue(); // Updated method name
//        assertThat(message.isReceiverDeleted()).isTrue(); // Updated method name
//    }
//
//    @Test
//    void testMessageToString() {
//        // Create a sample message
//        Message message = new Message();
//        message.setId(1L);
//        message.setMessageContent("Test message");
//        message.setSent(true);
//        message.setRead(false);
//        message.setFileUrl("http://example.com/testfile");
//        message.setSenderDeleted(false); // Updated method name
//        message.setReceiverDeleted(false); // Updated method name
//        message.setCreatedAt(LocalDateTime.now());
//
//        // Check the toString output
//        String expectedToString = "Message{id=1, sender=null, receiver=null, groupMessages=null, " +
//                "messageContent='Test message', sent=true, isRead=false, " +
//                "fileUrl='http://example.com/testfile', senderDeleted=false, receiverDeleted=false, createdAt=" + message.getCreatedAt() + '}';
//        assertThat(message.toString()).isEqualTo(expectedToString);
//    }
//}

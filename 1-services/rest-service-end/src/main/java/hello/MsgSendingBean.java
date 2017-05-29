//package hello;
//
//import java.util.Arrays;
//import java.util.List;
//import java.util.stream.Collectors;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.cloud.stream.annotation.EnableBinding;
//import org.springframework.cloud.stream.annotation.Output;
//import org.springframework.cloud.stream.messaging.Source;
//import org.springframework.messaging.MessageChannel;
//import org.springframework.messaging.support.MessageBuilder;
//import org.springframework.stereotype.Component;
//
//@Component
//@EnableBinding(Source.class)
//public class MsgSendingBean {
//
//    private Source source;
//
//    @Autowired
//    public MsgSendingBean(Source source) {
//        this.source = source;
//    }
//
//    public void sayHello(String value) {
//        source.output().send(MessageBuilder.withPayload(value).build());
//    }
//    
//    public void sayHellos(List<String> values) {
//	    source.output().send(MessageBuilder.withPayload(values).build());
//  }
//    
//    
////    @Autowired
////    private CustomSource customSource;
////    public interface CustomSource {
////        String OUTPUT = "customoutput";
////        @Output(CustomSource.OUTPUT)
////        MessageChannel output();
////    }
//}
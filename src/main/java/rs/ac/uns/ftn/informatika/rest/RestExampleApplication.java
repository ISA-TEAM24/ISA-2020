package rs.ac.uns.ftn.informatika.rest;

import net.devh.boot.grpc.server.autoconfigure.GrpcServerSecurityAutoConfiguration;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;


@SpringBootApplication(exclude = { GrpcServerSecurityAutoConfiguration.class })
public class RestExampleApplication {

	public static void main(String[] args) {
		SpringApplication.run(RestExampleApplication.class, args);
	}
	/*
	@Value("${myqueue}")
	String queue;

	@Value("${myqueue2}")
	String queue2;
	
	@Value("${myqueue3}")
	String psw2;

	@Value("${myexchange}")
	String exchange;
	
	@Value("${routingkey}")
	String routingkey;


	@Bean
	Queue queue() {
		return new Queue(queue, true);
	}

	@Bean
	Queue queue2() {
		return new Queue(queue2, true);
	}
	
	@Bean
	Queue psw2() {
		return new Queue(psw2, false, false, false, null);
	}

	@Bean
	DirectExchange exchange() {
		return new DirectExchange(exchange);
	}

	@Bean
	Binding binding1(Queue queue2, DirectExchange exchange) {
		return BindingBuilder.bind(queue2).to(exchange).with(routingkey);
	}
	
	@Bean
	Binding binding2(Queue queue, DirectExchange exchange) {
		return BindingBuilder.bind(queue).to(exchange).with(routingkey);
	}
	
	@Bean
	Binding binding3(Queue psw2, DirectExchange exchange) {
		return BindingBuilder.bind(psw2).to(exchange).with(routingkey);
	}
	
	@Bean
	public ConnectionFactory connectionFactory() {
		CachingConnectionFactory connectionFactory = new CachingConnectionFactory("localhost");
		return connectionFactory;
	}
	
	@Bean
	public MessageConverter jsonMessageConverter(){
	    return new Jackson2JsonMessageConverter();
	}

	public AmqpTemplate rabbitTemplate(ConnectionFactory connectionFactory) {
	      final RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory);
	      rabbitTemplate.setMessageConverter(jsonMessageConverter());
	      return rabbitTemplate;
	  }
	*/
}

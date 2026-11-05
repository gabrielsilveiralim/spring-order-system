package br.com.spring_order_system.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.QueueBuilder;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQConfig {

    public static final String EXCHANGE_PEDIDOS = "pedidos.exchange";
    public static final String FILA_PEDIDOS_CRIADOS = "pedidos.criados";
    public static final String ROUTING_KEY_PEDIDO_CRIADO = "pedido.criado";

    public static final String EXCHANGE_DLX = "pedidos.dlx";
    public static final String FILA_PEDIDOS_CRIADOS_DLQ = "pedidos.criados.dlq";
    public static final String ROUTING_KEY_DLQ = "pedido.criado.dlq";

    @Bean
    public DirectExchange pedidosExchange() {
        return new DirectExchange(EXCHANGE_PEDIDOS);
    }

    @Bean
    public DirectExchange pedidosDeadLetterExchange() {
        return new DirectExchange(EXCHANGE_DLX);
    }

    @Bean
    public Queue filaPedidosCriados() {
        return QueueBuilder.durable(FILA_PEDIDOS_CRIADOS)
                .withArgument("x-dead-letter-exchange", EXCHANGE_DLX)
                .withArgument("x-dead-letter-routing-key", ROUTING_KEY_DLQ)
                .build();
    }

    @Bean
    public Queue filaPedidosCriadosDlq() {
        return QueueBuilder.durable(FILA_PEDIDOS_CRIADOS_DLQ).build();
    }

    @Bean
    public Binding bindingPedidosCriados() {
        return BindingBuilder.bind(filaPedidosCriados())
                .to(pedidosExchange())
                .with(ROUTING_KEY_PEDIDO_CRIADO);
    }

    @Bean
    public Binding bindingPedidosCriadosDlq() {
        return BindingBuilder.bind(filaPedidosCriadosDlq())
                .to(pedidosDeadLetterExchange())
                .with(ROUTING_KEY_DLQ);
    }

    @Bean
    public MessageConverter jsonMessageConverter(ObjectMapper objectMapper) {
        return new Jackson2JsonMessageConverter(objectMapper);
    }
}
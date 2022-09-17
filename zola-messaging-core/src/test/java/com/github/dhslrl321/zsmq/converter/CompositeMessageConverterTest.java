package com.github.dhslrl321.zsmq.converter;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.*;

import com.github.dhslrl321.zsmq.SharedFixture;
import com.github.dhslrl321.zsmq.commons.Serializer;
import com.github.dhslrl321.zsmq.core.message.MediaTypes;
import com.github.dhslrl321.zsmq.core.message.ZolaMessage;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class CompositeMessageConverterTest {

    CompositeMessageConverter sut = new CompositeMessageConverter();

    @BeforeEach
    void setUp() {
        sut.register(new StringMessageConverter());
        sut.register(new JsonMessageConverter());
    }

    @Test
    void name() {
        assertThatThrownBy(() -> sut.isSupport("any")).isInstanceOf(UnsupportedOperationException.class);
    }

    @Test
    void work_jsonConverter() {
        ZolaMessage actual = sut.toMessage("ANY_QUEUE", new SharedFixture.Foo("hello"));

        assertThat(actual.getMediaType()).isEqualTo(MediaTypes.JSON);
    }

    @Test
    void work_jsonConverter2() {
        String actual = sut.fromMessage(SharedFixture.ANY_JSON_MESSAGE);

        assertThat(actual).isEqualTo("{\"bar\":\"hello\"}");
    }

    @Test
    void work_StringConverter() {
        ZolaMessage actual = sut.toMessage("ANY_QUEUE", "hello");

        assertThat(actual.getMediaType()).isEqualTo(MediaTypes.TEXT);
    }

    @Test
    void work_StringConverter2() {
        String actual = sut.fromMessage(SharedFixture.ANY_STRING_MESSAGE);

        assertThat(actual).isEqualTo("hello");
    }

    @Test
    void serializer_test() {
        String serialized = Serializer.serialize(SharedFixture.ANY_JSON_MESSAGE);

        System.out.println("serialized = " + serialized);
        ZolaMessage deserialize = Serializer.deserialize(serialized, ZolaMessage.class);
    }
}
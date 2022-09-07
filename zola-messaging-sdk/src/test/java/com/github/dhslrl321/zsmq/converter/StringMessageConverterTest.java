package com.github.dhslrl321.zsmq.converter;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

import com.github.dhslrl321.zsmq.message.MediaTypes;
import com.github.dhslrl321.zsmq.message.ZolaHeader;
import com.github.dhslrl321.zsmq.message.ZolaMessage;
import com.github.dhslrl321.zsmq.message.ZolaPayload;
import com.github.dhslrl321.zsmq.queue.QueueName;
import java.time.LocalDateTime;
import org.junit.jupiter.api.Test;

class StringMessageConverterTest {

    StringMessageConverter sut = new StringMessageConverter();

    static class Foo {
        String bar;
    }

    @Test
    void ifStringType() {
        boolean actual = sut.isSupport("");

        assertThat(actual).isTrue();
    }

    @Test
    void ifNotStringType() {
        boolean actual = sut.isSupport(new Foo());

        assertThat(actual).isFalse();
    }

    @Test
    void name() {
        ZolaMessage actual = sut.toMessage("any name", "any payload");

        assertThat(actual.getZolaPayload().getValue()).isEqualTo("any payload");
    }

    @Test
    void name2() {
        ZolaMessage message = ZolaMessage.of(ZolaHeader.of(QueueName.of("any queue name"), LocalDateTime.now(), MediaTypes.TEXT),
                ZolaPayload.of("any payload"));

        String actual = sut.fromMessage(message);

        assertThat(actual).isEqualTo("any payload");
    }
}
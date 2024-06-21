package io.github.maidsg.starter.voyage.component.serializers;

import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.JSONFactory;
import com.alibaba.fastjson2.JSONReader;
import com.alibaba.fastjson2.JSONWriter;
import com.alibaba.fastjson2.support.redission.JSONCodec;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufAllocator;
import io.netty.buffer.ByteBufInputStream;
import io.netty.buffer.ByteBufOutputStream;
import org.redisson.client.codec.BaseCodec;
import org.redisson.client.protocol.Decoder;
import org.redisson.client.protocol.Encoder;
import org.redisson.codec.Kryo5Codec;
import org.springframework.context.annotation.Bean;

import java.io.IOException;

/*******************************************************************
 * <pre></pre>
 * @文件名称： FastJsonCodec.java
 * @包 路  径： io.github.maidsg.starter.start.model.codc
 * @Copyright：wy (C) 2024 *
 * @Description:
 * @Version: V1.0
 * @Author： wy
 * @Date：2024/3/22 22:07
 * @Modify：
 */
public class RedissonFastJsonCodec extends JSONCodec {



    private static final JSONWriter.Feature[] writerFeatures = {JSONWriter.Feature.WriteClassName};
    private static final JSONReader.Feature[] readerFeatures = {};
    private static final JSONReader.AutoTypeBeforeHandler autoTypeFilter = JSONReader.autoTypeFilter(Bean.class.getName()); // 配置反序列化支持的类，支持前缀配置



    // 用于spring-session-redis的序列化
    public RedissonFastJsonCodec(ClassLoader classLoader, RedissonFastJsonCodec codec) {
        super(JSONFactory.createWriteContext(writerFeatures),
                JSONFactory.createReadContext(autoTypeFilter, readerFeatures));

    }

    public RedissonFastJsonCodec() {
        super(JSONFactory.createWriteContext(writerFeatures),
                JSONFactory.createReadContext(autoTypeFilter, readerFeatures));
    }

   public static final RedissonFastJsonCodec INSTANCE = new RedissonFastJsonCodec();

//    private final Encoder encoder = in -> {
//        ByteBuf out = ByteBufAllocator.DEFAULT.buffer();
//        try {
//            ByteBufOutputStream os = new ByteBufOutputStream(out);
//            JSON.writeTo(os, in, JSONWriter.Feature.WriteClassName);
//            return os.buffer();
//        } catch (Exception e) {
//            out.release();
//            throw new IOException(e);
//        }
//    };
//
//    private final Decoder<Object> decoder = (buf, state) ->
//            JSON.parseObject(new ByteBufInputStream(buf), Object.class);

    @Override
    public Decoder<Object> getValueDecoder() {
        return super.getValueDecoder();
    }

    @Override
    public Encoder getValueEncoder() {
        return super.getValueEncoder();
    }
}

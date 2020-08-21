package com.hyperloop.UDPSpaceX;

import java.io.InputStream;
import java.io.StringReader;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.DatagramChannel;
import java.util.Properties;

import org.mule.api.MuleEventContext;
import org.mule.api.lifecycle.Callable;

import com.google.gson.Gson;
import com.google.gson.stream.JsonReader;

import javolution.io.Struct;

public class SpaceXTransfomer implements Callable {

	static Properties propertyFile = new Properties();

	@Override
	public Object onCall(MuleEventContext context) throws Exception {
		InputStream input = SpaceXTransfomer.class.getClassLoader().getResourceAsStream("hyperloop.properties");
		propertyFile.load(input);
		Gson gson = new Gson();
		JsonReader reader = new JsonReader(new StringReader(context.getMessageAsString()));
		reader.setLenient(true);
		SpaceXData data = gson.fromJson(reader, SpaceXData.class);
		SpaceXPacket packet = new SpaceXPacket();
		packet.team_id.set((byte) data.getTeam_id());
		packet.status.set((byte) data.getStatus());
		packet.acceleration.set(data.getAcceleration());
		packet.position.set(data.getPosition());
		packet.velocity.set(data.getVelocity());
		packet.battery_voltage.set(data.getVoltage());
		packet.battery_current.set(data.getCurrent());
		packet.battery_temperature.set(data.getBat_temp());
		packet.pod_temperature.set(data.getPod_temp());
		packet.stripe_count.set(data.getStripe_count());
		ByteBuffer buf = ByteBuffer.allocate(34);
		buf.clear();
		buf.put(packet.getByteBuffer());
		buf.flip();

		DatagramChannel channel = DatagramChannel.open();
		channel.send(buf, new InetSocketAddress(propertyFile.getProperty("udp.spacex.host"),
				Integer.parseInt(propertyFile.getProperty("udp.spacex.port"))));

		return null;

	}

	/** Simple struct containing book information. */
	class SpaceXPacket extends Struct {

		Unsigned8 team_id = new Unsigned8();
		Unsigned8 status = new Unsigned8();
		Signed32 acceleration = new Signed32();
		Signed32 position = new Signed32();
		Signed32 velocity = new Signed32();
		Signed32 battery_voltage = new Signed32();
		Signed32 battery_current = new Signed32();
		Signed32 battery_temperature = new Signed32();
		Signed32 pod_temperature = new Signed32();
		Unsigned32 stripe_count = new Unsigned32();

		public boolean isPacked() {
			return true; // MyStruct is packed.
		}
	}

}

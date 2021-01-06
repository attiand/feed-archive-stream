package com.github.attiand.assertj.jaxrs.json.asserts;

import static org.assertj.core.api.Assertions.within;

import javax.json.Json;
import javax.json.JsonObject;

import org.junit.jupiter.api.Test;

class JsonObjectAssertTest {

	private static final JsonObject JSON_OBJ = Json.createObjectBuilder()
			.add("firstName", "John")
			.add("lastName", "Smith")
			.add("age", 25)
			.add("double", 2.5d)
			.add("male", true)
			.add("female", false)
			.add("address", Json.createObjectBuilder().add("streetAddress", "21 2nd Street").add("city", "New York").add("state", "NY"))
			.add("phoneNumber",
					Json.createArrayBuilder()
							.add(Json.createObjectBuilder().add("number", "212 555-1234"))
							.add(Json.createObjectBuilder().add("number", "646 555-4567")))
			.build();

	@Test
	void shouldAcceptJsonPointerAsString() {
		JsonObjectAssert.assertThat(JSON_OBJ).path("/phoneNumber/0/number").asString().isEqualTo("212 555-1234");
	}

	@Test
	void shouldAcceptJsonPointerAsInteger() {
		JsonObjectAssert.assertThat(JSON_OBJ).path("/age").asInteger().isEqualTo(25);
	}

	@Test
	void shouldAcceptJsonPointerAsDouble() {
		JsonObjectAssert.assertThat(JSON_OBJ).path("/double").asDouble().isEqualTo(2.5, within(0.1));
	}

	@Test
	void shouldAcceptJsonPointerAsTrue() {
		JsonObjectAssert.assertThat(JSON_OBJ).path("/male").asBoolean().isTrue();
	}

	@Test
	void shouldAcceptJsonPointerAsFalse() {
		JsonObjectAssert.assertThat(JSON_OBJ).path("/female").asBoolean().isFalse();
	}
}

package io.nova41.leopard.models;

import java.util.LinkedHashMap;
import java.util.Map;

import org.bukkit.configuration.serialization.ConfigurationSerializable;
import org.bukkit.configuration.serialization.SerializableAs;

@SerializableAs("Dataset")
public class Dataset implements Cloneable, ConfigurationSerializable {
	/**
	 * A class only storing double arrays and the category of that array, it is
	 * serializable and could work with SnakeYAML.
	 * 
	 * @author Nova41
	 * 
	 */

	public String category;
	public Double[] data;

	public Dataset(String category, Double[] data) {
		this.category = category;
		this.data = data;
	}

	public Map<String, Object> serialize() {
		LinkedHashMap<String, Object> result = new LinkedHashMap<String, Object>();
		result.put("category", this.category);
		result.put("data", this.data);
		return result;
	}

	public Dataset deserialize(Map<String, Object> args) {
		String category = "null";
		Double[] data = new Double[0];
		if (args.containsKey("category"))
			category = (String) args.get("category");
		if (args.containsKey("data"))
			data = (Double[]) args.get("data");
		return new Dataset(category, data);
	}
}

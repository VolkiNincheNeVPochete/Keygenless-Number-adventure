package com.kursach.keynumadv.world.map;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.File;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class NormalParser {

    // ========== Контейнеры данных ==========

    /**
     * Парсит TMX файл из пути
     */
    public static TmxMap parse(File file) throws Exception {
        return parse(file.toURI().toURL().openStream());
    }

    /**
     * Парсит TMX из InputStream
     */
    public static TmxMap parse(InputStream input) throws Exception {
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        factory.setFeature("http://apache.org/xml/features/disallow-doctype-decl", true);
        factory.setFeature("http://xml.org/sax/features/external-general-entities", false);
        factory.setFeature("http://xml.org/sax/features/external-parameter-entities", false);
        factory.setFeature("http://apache.org/xml/features/nonvalidating/load-external-dtd", false);

        DocumentBuilder builder = factory.newDocumentBuilder();
        Document doc = builder.parse(input);
        doc.getDocumentElement().normalize();

        return parseDocument(doc);
    }

    /**
     * Парсит из String (XML content)
     */
    public static TmxMap parseString(String xmlContent) throws Exception {
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        factory.setFeature("http://apache.org/xml/features/disallow-doctype-decl", true);
        DocumentBuilder builder = factory.newDocumentBuilder();
        Document doc = builder.parse(new org.xml.sax.InputSource(new java.io.StringReader(xmlContent)));
        doc.getDocumentElement().normalize();
        return parseDocument(doc);
    }

    private static TmxMap parseDocument(Document doc) {
        TmxMap map = new TmxMap();
        Element root = doc.getDocumentElement();

        // Атрибуты карты
        map.version = root.getAttribute("version");
        map.orientation = root.getAttribute("orientation");
        map.width = parseInt(root.getAttribute("width"), 0);
        map.height = parseInt(root.getAttribute("height"), 0);
        map.tileWidth = parseInt(root.getAttribute("tilewidth"), 0);
        map.tileHeight = parseInt(root.getAttribute("tileheight"), 0);

        // Tilesets
        NodeList tilesets = root.getElementsByTagName("tileset");
        for (int i = 0; i < tilesets.getLength(); i++) {
            Element ts = (Element) tilesets.item(i);
            String name = ts.getAttribute("name");
            String source = ts.getAttribute("source");
            if (!name.isEmpty() && !source.isEmpty()) {
                map.tilesets.put(name, source);
            }
        }

        // Слои и объекты
        NodeList layers = root.getElementsByTagName("layer");
        for (int i = 0; i < layers.getLength(); i++) {
            Element layerElem = (Element) layers.item(i);
            TmxLayer layer = parseLayer(layerElem);
            if (layer != null) map.layers.add(layer);
        }

        // Object groups (тоже считаются слоями в TMX)
        NodeList objectGroups = root.getElementsByTagName("objectgroup");
        for (int i = 0; i < objectGroups.getLength(); i++) {
            Element groupElem = (Element) objectGroups.item(i);
            TmxLayer layer = parseObjectGroup(groupElem);
            if (layer != null) map.layers.add(layer);
        }

        return map;
    }

    // ========== MAIN PARSER ==========

    private static TmxLayer parseLayer(Element layerElem) {
        TmxLayer layer = new TmxLayer();
        layer.name = layerElem.getAttribute("name");
        layer.id = parseInt(layerElem.getAttribute("id"), -1);
        layer.width = parseInt(layerElem.getAttribute("width"), 0);
        layer.height = parseInt(layerElem.getAttribute("height"), 0);
        // Данные тайлов можно распарсить отдельно если нужно
        return layer;
    }

    private static TmxLayer parseObjectGroup(Element groupElem) {
        TmxLayer layer = new TmxLayer();
        layer.name = groupElem.getAttribute("name");
        layer.id = parseInt(groupElem.getAttribute("id"), -1);

        NodeList objects = groupElem.getElementsByTagName("object");
        for (int i = 0; i < objects.getLength(); i++) {
            Element objElem = (Element) objects.item(i);
            TmxObject obj = parseObject(objElem);
            if (obj != null) layer.objects.add(obj);
        }

        return layer;
    }

    private static TmxObject parseObject(Element objElem) {
        if (objElem == null) return null;

        TmxObject obj = new TmxObject();
        obj.id = parseInt(objElem.getAttribute("id"), -1);
        obj.name = nullIfEmpty(objElem.getAttribute("name"));
        obj.type = objElem.getAttribute("type");
        obj.x = parseFloat(objElem.getAttribute("x"), 0f);
        obj.y = parseFloat(objElem.getAttribute("y"), 0f);
        obj.width = parseFloat(objElem.getAttribute("width"), 0f);
        obj.height = parseFloat(objElem.getAttribute("height"), 0f);
        obj.template = nullIfEmpty(objElem.getAttribute("template"));
        obj.gid = nullIfEmpty(objElem.getAttribute("gid"));

        // === ПАРСИМ СВОЙСТВА ===
        NodeList propsList = objElem.getElementsByTagName("properties");
        if (propsList.getLength() > 0) {
            Element propsContainer = (Element) propsList.item(0);
            NodeList properties = propsContainer.getElementsByTagName("property");

            for (int i = 0; i < properties.getLength(); i++) {
                Element propElem = (Element) properties.item(i);
                String propName = propElem.getAttribute("name");
                String propType = propElem.getAttribute("type");
                String propValue = propElem.getAttribute("value");

                if (!propName.isEmpty()) {
                    obj.properties.put(propName, new TmxProperty(propName, propType, propValue));
                }
            }
        }

        return obj;
    }

    // ========== Внутренняя логика парсинга ==========

    private static int parseInt(String s, int def) {
        if (s == null || s.isEmpty()) return def;
        try {
            return Integer.parseInt(s);
        } catch (NumberFormatException e) {
            return def;
        }
    }

    private static float parseFloat(String s, float def) {
        if (s == null || s.isEmpty()) return def;
        try {
            return Float.parseFloat(s);
        } catch (NumberFormatException e) {
            return def;
        }
    }

    private static String nullIfEmpty(String s) {
        return (s == null || s.isEmpty()) ? null : s;
    }

    public static TmxLayer getLayerByName(TmxMap map, String layerName) {
        if (map == null || layerName == null) return null;
        for (TmxLayer layer : map.layers) {
            if (layerName.equals(layer.name)) return layer;
        }
        return null;
    }

    // ========== Helpers ==========

    public static List<TmxObject> getObjectsByType(TmxMap map, String objectType) {
        List<TmxObject> result = new ArrayList<>();
        if (map == null) return result;
        for (TmxLayer layer : map.layers) {
            for (TmxObject obj : layer.objects) {
                if (objectType != null && objectType.equals(obj.type)) {
                    result.add(obj);
                } else if (objectType == null && (obj.type == null || obj.type.isEmpty())) {
                    result.add(obj);
                }
            }
        }
        return result;
    }

    public static class TmxProperty {
        public final String name;
        public final String type;   // "string", "int", "float", "bool"
        public final String value;  // сырое значение

        public TmxProperty(String name, String type, String value) {
            this.name = name;
            this.type = type != null ? type : "string";
            this.value = value != null ? value : "";
        }

        // === Typed getters с дефолтами ===
        public String getString() {
            return value;
        }

        public int getInt(int defaultValue) {
            if (value == null || value.isEmpty()) return defaultValue;
            try {
                return Integer.parseInt(value);
            } catch (NumberFormatException e) {
                return defaultValue;
            }
        }

        public float getFloat(float defaultValue) {
            if (value == null || value.isEmpty()) return defaultValue;
            try {
                return Float.parseFloat(value);
            } catch (NumberFormatException e) {
                return defaultValue;
            }
        }

        public boolean getBool(boolean defaultValue) {
            if (value == null || value.isEmpty()) return defaultValue;
            return value.equalsIgnoreCase("true") || value.equals("1");
        }

        @Override
        public String toString() {
            return name + "=" + value + " (" + type + ")";
        }
    }

    public static class TmxObject {
        public int id = -1;
        public String name;
        public String type = "";
        public float x, y;
        public float width, height;
        public String template;
        public String gid;
        public Map<String, TmxProperty> properties = new HashMap<>();

        // === Convenience getters ===
        public TmxProperty getProperty(String key) {
            return properties.get(key);
        }

        public String getString(String key, String def) {
            TmxProperty p = properties.get(key);
            return p != null ? p.getString() : def;
        }

        public float getFloat(String key, float def) {
            TmxProperty p = properties.get(key);
            return p != null ? p.getFloat(def) : def;
        }

        public int getInt(String key, int def) {
            TmxProperty p = properties.get(key);
            return p != null ? p.getInt(def) : def;
        }

        public boolean getBool(String key, boolean def) {
            TmxProperty p = properties.get(key);
            return p != null ? p.getBool(def) : def;
        }

        public boolean hasProperty(String key) {
            return properties.containsKey(key);
        }

        @Override
        public String toString() {
            return "TmxObject{id=" + id + ", type='" + type + "', pos=(" + x + "," + y + "), props=" + properties + "}";
        }
    }

    // ========== Convenience: получить слой по имени ==========

    public static class TmxLayer {
        public String name;
        public int id;
        public int width, height;
        public List<TmxObject> objects = new ArrayList<>();

        @Override
        public String toString() {
            return "TmxLayer{name='" + name + "', objects=" + objects.size() + "}";
        }
    }

    public static class TmxMap {
        public String version;
        public String orientation;
        public int width, height;
        public int tileWidth, tileHeight;
        public List<TmxLayer> layers = new ArrayList<>();
        public Map<String, String> tilesets = new HashMap<>();
    }
}

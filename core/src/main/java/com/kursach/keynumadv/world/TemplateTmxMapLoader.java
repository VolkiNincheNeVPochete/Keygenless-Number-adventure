package com.kursach.keynumadv.world;


import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.maps.ImageResolver;
import com.badlogic.gdx.maps.MapObjects;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.utils.XmlReader;

public class TemplateTmxMapLoader extends TmxMapLoader {
    FileHandle tmxFile;
    @Override
    protected TiledMap loadTiledMap(FileHandle tmxFile, TmxMapLoader.Parameters parameter, ImageResolver imageResolver) {
        this.tmxFile = tmxFile;
        return super.loadTiledMap(tmxFile, parameter, imageResolver);
    }
    @Override
    protected void loadObject(TiledMap map, MapObjects objects, XmlReader.Element element, float heightInPixels) {
        if (!element.getName().equals("object")) {
            return;
        }

        if (!element.hasAttribute("template")) {
            super.loadObject(map, objects, element, heightInPixels);
            return;
        }

        FileHandle templateFile = getRelativeFileHandle(tmxFile, element.getAttribute("template"));
        XmlReader.Element templateRoot = xml.parse(templateFile);

        XmlReader.Element templateObj = templateRoot.getChildByName("object");
        if (templateObj == null) {
            return;
        }
        XmlReader.Element merged = new XmlReader.Element("object", templateRoot);

        for (String attr : templateObj.getAttributes().keys()) {
            merged.setAttribute(attr, templateObj.getAttribute(attr));
        }

        merged.setAttribute("x", element.getAttribute("x"));
        merged.setAttribute("y", element.getAttribute("y"));
        merged.setAttribute("id", element.getAttribute("id"));

        if (element.getChildren() != null) {
            for (XmlReader.Element child : element.getChildren()) {
                if ("properties".equals(child.getName())) {
                    merged.addChild(child);
                }
            }
        }

        super.loadObject(map, objects, merged, heightInPixels);
    }
}

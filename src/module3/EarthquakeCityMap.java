package module3;

import de.fhpotsdam.unfolding.UnfoldingMap;
import de.fhpotsdam.unfolding.data.PointFeature;
import de.fhpotsdam.unfolding.marker.Marker;
import de.fhpotsdam.unfolding.marker.SimplePointMarker;
import de.fhpotsdam.unfolding.providers.MBTilesMapProvider;
import de.fhpotsdam.unfolding.providers.Microsoft;
import de.fhpotsdam.unfolding.utils.MapUtils;
import parsing.ParseFeed;
import processing.core.PApplet;

import java.util.ArrayList;
import java.util.List;

/**
 * EarthquakeCityMap
 * An application with an interactive map displaying earthquake data.
 * Author: UC San Diego Intermediate Software Development MOOC team
 *
 * @author Ivan Vasilev
 */
public class EarthquakeCityMap extends PApplet {

  // You can ignore this.  It's to keep eclipse from generating a warning.
  private static final long serialVersionUID = 1L;

  // IF YOU ARE WORKING OFFLINE, change the value of this variable to true
  private static final boolean offline = false;

  // Less than this threshold is a light earthquake
  public static final float THRESHOLD_MODERATE = 5;
  // Less than this threshold is a minor earthquake
  public static final float THRESHOLD_LIGHT = 4;

  /**
   * This is where to find the local tiles, for working without an Internet connection
   */
  public static String mbTilesString = "blankLight-1-3.mbtiles";

  // The map
  private UnfoldingMap map;

  //feed with magnitude 2.5+ Earthquakes
  private String earthquakesURL = "https://earthquake.usgs.gov/earthquakes/feed/v1.0/summary/2.5_week.atom";

  public void setup() {
    size(950, 600, OPENGL);

    if (offline) {
      map = new UnfoldingMap(this, 200, 50, 700, 500, new MBTilesMapProvider(mbTilesString));
      earthquakesURL = "2.5_week.atom";  // Same feed, saved Aug 7, 2015, for working offline
    } else {
      //											new Microsoft.RoadProvider()	new Google.GoogleMapProvider()
      map = new UnfoldingMap(this, 200, 50, 700, 500, new Microsoft.RoadProvider());
      // IF YOU WANT TO TEST WITH A LOCAL FILE, uncomment the next line
      //earthquakesURL = "2.5_week.atom";
    }

    map.zoomToLevel(2);
    MapUtils.createDefaultEventDispatcher(this, map);

    // The List you will populate with new SimplePointMarkers
    List<Marker> markers = new ArrayList<Marker>();

    //Use provided parser to collect properties for each earthquake
    //PointFeatures have a getLocation method
    List<PointFeature> earthquakes = ParseFeed.parseEarthquake(this, earthquakesURL);

    // loop through PointFeature list earthquakes, call createMarker and add to markers List
    for (PointFeature feature : earthquakes) {
      markers.add(createMarker(feature));
    }

    // Add the markers to the map so that they are displayed
    map.addMarkers(markers);
  }

  //  createMarker: A suggested helper method that takes in an earthquake
//  feature and returns a SimplePointMarker for that earthquake
  private SimplePointMarker createMarker(PointFeature feature) {
    // To print all of the features in a PointFeature (so you can see what they are)
    // uncomment the line below.  Note this will only print if you call createMarker
    // from setup
    //System.out.println(feature.getProperties());

    // Create a new SimplePointMarker at the location given by the PointFeature
    SimplePointMarker marker = new SimplePointMarker(feature.getLocation());

    Object magObj = feature.getProperty("magnitude");
    float mag = Float.parseFloat(magObj.toString());

    int markerColor = color(0, 0, 255);
    float markerRadius = 5;

    if (mag >= THRESHOLD_LIGHT && mag < THRESHOLD_MODERATE) {
      markerColor = color(255, 255, 0);
      markerRadius = 10;
    }
    if (mag >= THRESHOLD_MODERATE) {
      markerColor = color(255, 0, 0);
      markerRadius = 15;
    }
    marker.setColor(markerColor);
    marker.setRadius(markerRadius);
    // Finally return the marker
    return marker;
  }

  public void draw() {
    background(10);
    map.draw();
    addKey();
  }


  // helper method to draw key in GUI
  private void addKey() {
    fill(255);
    rect(25, 50, 150, 200);
    textSize(12);
    fill(0);
    text("Earthquake Key :", 35, 80);
    //draw circles
    int[] color = {color(255, 0, 0), color(255, 255, 0), color(0, 0, 255)};
    int circleX = 35;
    int circleY = 80;
    int circleRadius1 = 20;
    int circleRadius2 = 20;
    for (int i = 0; i < color.length; i++) {
      fill(color[i]);
      ellipse(circleX, circleY += 30, circleRadius1 -= 5, circleRadius2 -= 5);
    }
    //Print Legend's text
    String[] magText = {"5.0+ Magnitude", "4.0+ Magnitude", "Below 4.0"};
    int y = 85;
    for (int i = 0; i < magText.length; i++) {
      fill(0);
      text(magText[i], 50, y += 30);
    }
  }
}

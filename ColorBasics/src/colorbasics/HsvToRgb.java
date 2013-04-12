package colorbasics;
// Convert from HSV values (0 - 100) to RGB values (0 - 255)
public class HsvToRgb {
  
  final int MAX_INTENSITY = 255;
  
  int red;
  int green;
  int blue;
  
  HsvToRgb(int h, int s, int v)
  {
    int i;
    double hh, ss, vv;
    double f, p, q, t, r, g, b;
    
    // Make sure our arguments stay in-range
    h = Math.max(0, Math.min(100, h));
    s = Math.max(0, Math.min(100, s));
    v = Math.max(0, Math.min(100, v));
    if( s == 0 ) {
      // achromatic (grey)
      red = MAX_INTENSITY * v / 100;
      green = MAX_INTENSITY * v / 100;
      blue = MAX_INTENSITY * v / 100;
      return;
    }
    
    hh = (double)(h) * 6 / 100;   // (h * 360 / 100 / 60) sector 0 to 5
    ss = (double)(s) / 100;
    vv = (double)(v) / 100;
    i = (int) Math.floor( hh );
    f = hh - i;   // factorial part of h
    p = vv * ( 1 - ss );
    q = vv * ( 1 - ss * f );
    t = vv * ( 1 - ss * ( 1 - f ) );
    
    switch( i ) {
      case 0:
        r = vv;
        g = t;
        b = p;
        break;
      case 1:
        r = q;
        g = vv;
        b = p;
        break;
      case 2:
        r = p;
        g = vv;
        b = t;
        break;
      case 3:
        r = p;
        g = q;
        b = vv;
        break;
      case 4:
        r = t;
        g = p;
        b = vv;
        break;
      default:  // case 5:
        r = vv;
        g = p;
        b = q;
        break;
    }
    red = (int) Math.round(r * 255);
    green = (int) Math.round(g * 255);
    blue = (int) Math.round(b * 255);
  }
  
  public int getRed() {
    return red;
  }
  
  public int getGreen() {
    return green;
  }
  
  public int getBlue() {
    return blue;
  }
}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package evolith.helpers;

import java.awt.Font;
import java.awt.FontFormatException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
/**
 *
 * @author ErickFrank
 */
public class FontLoader  {
    InputStream is;
    String fName;
    
    public FontLoader(String fName) throws FontFormatException, IOException
    {
        this.fName = fName;
        this.is = FontLoader.class.getResourceAsStream(fName);
        Font generic = Font.createFont(Font.TRUETYPE_FONT, is);
    }
}

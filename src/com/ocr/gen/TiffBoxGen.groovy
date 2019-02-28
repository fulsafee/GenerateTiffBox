package com.ocr.gen

import java.awt.Font

import net.sourceforge.tessboxeditor.TessTrainer
import net.sourceforge.tessboxeditor.TiffBoxGenerator
import net.sourceforge.tessboxeditor.utilities.Utils

class TiffBoxGen {
	
	String tessDirectory="C:/Program Files (x86)/Tesseract-OCR";
	
	public TiffBoxGen() {
	}
	

    String generateTiffBox(String text, String urd, String fontName, int fontStyle, int fontSize, String lineNo, String outputDirectory, boolean text2Image) throws Exception {
        String prefix = urd;
		def fontGen = new Font(fontName,fontStyle,fontSize);
		String fileName = createFileName(fontGen) + "." + lineNo + ".tif";
		
        if (prefix.trim().length() > 0) {
            prefix += ".";
        }

        long lastModified = 0;
        File fontpropFile = new File(outputDirectory, prefix + "font_properties");
        if (fontpropFile.exists()) {
            lastModified = fontpropFile.lastModified();
        }

        if (text2Image) {
            // execute Text2Image
            TessTrainer trainer = new TessTrainer(tessDirectory, outputDirectory, urd, "", false);
            String outputbase = fileName;
            if (outputbase.endsWith(".tif")) {
                outputbase = outputbase.substring(0, outputbase.lastIndexOf(".tif"));
            }
            outputbase = outputDirectory + "/" + prefix + outputbase;
            trainer.text2image(inputTextFile, outputbase, fontGen, "C:\\Windows\\Fonts", 0, 0, 12, 2550, 3300);
//            Utils.removeEmptyBoxes(new File(outputbase + ".box"));
        } else {
            TiffBoxGenerator generator = new TiffBoxGenerator(text, fontGen, 2550, 3300);
            generator.setOutputFolder(new File(outputDirectory));
            generator.setFileName(prefix + fileName);
            generator.setTracking(0);
            generator.setLeading(0);
            generator.setNoiseAmount(0);
            generator.setAntiAliasing(false);
            generator.create();
        }

        // updates font_properties file
        Utils.updateFontProperties(new File(outputDirectory), prefix + fileName, fontGen);
        String msg = String.format("TIFF/Box files have been generated and saved in %s folder.", outputDirectory);

        if (fontpropFile.exists() && lastModified != fontpropFile.lastModified()) {
            msg = msg.concat("\nBe sure to check the entries in font_properties file for accuracy.");
        }

        return msg;
    }
	
	/**
	 * Creates file name.
	 *
	 * @param font
	 * @return file name
	 */
	String createFileName(Font font) {
		return font.getName().replace(" ", "").toLowerCase() + (font.isBold() ? "b" : "") + (font.isItalic() ? "i" : "");
	}
}

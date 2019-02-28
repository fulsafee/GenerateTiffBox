package com.ocr.gen

class Generator {

	public static void main(String[] args) {
		TiffBoxGen gen = new TiffBoxGen()

		if(args.length < 8) {
			println 'Please provide arguments textFilePath lang fontName fontStyle fontSize lineName outDir isImage2Text'
			return
		}

		//gen.generateTiffBox("Simple One Line Text Here", "eng","Arial",0,12, "exp0", "D:/OCR/test", false);
		
		try {
			File fileDir = new File(args[0]);
				
			BufferedReader inputStr = new BufferedReader(new InputStreamReader(new FileInputStream(fileDir), "UTF8"));
					
			String str;
			int i=0;
				  
			while ((str = inputStr.readLine()) != null) {
				gen.generateTiffBox(str, args[1], args[2], Integer.parseInt(args[3]), Integer.parseInt(args[4]), args[5]+(i++), args[6], Boolean.parseBoolean(args[7]));
			}
					
					inputStr.close();
			}
			catch (UnsupportedEncodingException e)
			{
				System.out.println(e.getMessage());
			}
			catch (IOException e)
			{
				System.out.println(e.getMessage());
			}
			catch (Exception e)
			{
				System.out.println(e.getMessage());
			}

//		new ByteArrayInputStream( args[0] ).eachLine('UTF-8') { line ->
//			gen.generateTiffBox(line, args[1], args[2], Integer.parseInt(args[3]), Integer.parseInt(args[4]), args[5], args[6], Boolean.parseBoolean(args[7]));
//		}
	}
}

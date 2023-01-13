package com.convert;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

import com.itextpdf.html2pdf.ConverterProperties;
import com.itextpdf.html2pdf.HtmlConverter;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.styledxmlparser.jsoup.Jsoup;
import com.itextpdf.styledxmlparser.jsoup.nodes.Document;
import com.itextpdf.styledxmlparser.jsoup.nodes.Element;
import com.itextpdf.styledxmlparser.jsoup.parser.Tag;
import com.itextpdf.styledxmlparser.jsoup.select.Elements;

/**
 * 
 * @author Hrushikesh.Kadam
 *
 */
public class HtmlToPdf {

	/**
	 * Function which converts HTML to String. Inserting pageCountTag tag into Header tag
	 * by split string and concat the final String
	 * Inserting copyRigntTag tag into Header tag by split string and concat the final String
	 * 
	 * @param htmlSourcePath
	 * @param pdfDestnationPath
	 */
	public static void htmlToPdf(String htmlSourcePath, String pdfDestnationPath) {
		String pageCountTag = "@page {@bottom-right {	content: \"Page \" counter(page) \" of \" counter(pages);}}";
		String copyRightTag = "@page {@bottom-left {	content: \"  © 2023 Winjit Technologies | All Rights Reserved \"; color:red}}";

		String charsetName = "UTF-8";
		Elements element = null;
		Element style = null;
		Document document = null;
		try {
			document = Jsoup.parse(new File(htmlSourcePath), charsetName);
			element = document.select("head");

			style = new Element(Tag.valueOf("style"), "");
			style.appendText(pageCountTag);
			style.append(copyRightTag);
			element.get(0).appendChild(style);

			new HtmlToPdf().createPDF(document.toString(), pdfDestnationPath);

		} catch (Exception e1) {
			e1.getMessage();
			e1.printStackTrace();
		}

	}

	/**
	 * Static function which loads the CSS present in HTML file.
	 * 
	 * @param path
	 * @param charsetName
	 * @return
	 * @throws IOException
	 */
	public static String loadCssFileContent(String path, String charsetName) throws IOException {
		byte[] encoded = Files.readAllBytes(java.nio.file.Paths.get(path));
		return new String(encoded, charsetName);

	}

	/**
	 * Converts HTML String to PDF file using HTML Converter.
	 * 
	 * @param htmlFile
	 * @param dest
	 */
	private void createPDF(String htmlFile, String destination) {
		try {
			PdfWriter pdfWriter = new PdfWriter(destination);
			PdfDocument pdfDocument = new PdfDocument(pdfWriter);
			ConverterProperties converterProperties = new ConverterProperties().setBaseUri("");
			HtmlConverter.convertToPdf(htmlFile, pdfDocument, converterProperties);
			pdfDocument.close();
			pdfWriter.close();
		} catch (Exception exception) {
			exception.getMessage();
			exception.printStackTrace();
		}
	}

	/**
	 * Main Function
	 * 
	 * @param args
	 * @throws IOException
	 */
	public static void main(String[] args) throws IOException {

		HtmlToPdf.htmlToPdf("D:\\Assignment\\HTML to PDF\\HTML_To_PDF_Conversion\\405.html", "ook.pdf");
	}
}

/**
 * 
 */
package server.dao;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.nio.channels.FileLock;

/**
 * @author geovan.goes
 *
 */
public class FileUtil
{
	/***
	 * 
	 * @param fullFileName
	 * @return
	 * @throws FileNotFoundException
	 * @throws IOException
	 */
	public static StringBuffer obterConteudoDeUmArquivo(String fullFileName) throws FileNotFoundException, IOException 
	{
		File file = new File(fullFileName);
		
		return obterConteudoDeUmArquivo(file);
	}
	
	/***
	 * 
	 * @param file
	 * @return
	 * @throws FileNotFoundException
	 * @throws IOException
	 */
	public static StringBuffer obterConteudoDeUmArquivo(File file) throws FileNotFoundException, IOException
	{
		FileInputStream fis = new FileInputStream(file);
		
		InputStreamReader inputStreamReader = new InputStreamReader(fis);
		
		BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
		
		StringBuffer stringBuffer = new StringBuffer();
		String line = null;
		
		
		while ((line = bufferedReader.readLine()) != null)
		{
			stringBuffer.append(line);
			stringBuffer.append("\n");
		}
		
		bufferedReader.close();
		fis.close();
		inputStreamReader.close();
		
		return stringBuffer;
	}
	
	/***
	 * 
	 * @param fileNameComplete
	 * @param content
	 * @return
	 */
	public static boolean gerarArquivo(String fileNameComplete, String content)
	{
		boolean result = true;
		
		try 
		{	
			File entrada = new File(fileNameComplete);
			
			FileOutputStream fileOutputStream = new FileOutputStream(entrada);
			
			FileLock lock = fileOutputStream.getChannel().lock();
			
			OutputStreamWriter outputStreamWriter = new OutputStreamWriter(fileOutputStream);
			
			BufferedWriter writer = new BufferedWriter(outputStreamWriter);
			
			writer.write(content);
			writer.flush();
			
			lock.release();
			writer.close();
		} 
		catch (IOException e) 
		{
			e.printStackTrace();
			result = false;
		}
		
		return result;
	}
}

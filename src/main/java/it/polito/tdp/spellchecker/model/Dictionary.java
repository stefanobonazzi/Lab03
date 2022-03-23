package it.polito.tdp.spellchecker.model;

import java.io.*;
import java.util.*;

public class Dictionary {

	private List<String> words;
	
	public void loadDictionary(String language) {
		String languageFile;
		
		if(language.equals("Italiano"))
			languageFile = "Italian.txt";
		else
			languageFile = "English.txt";
		 
		try {
			FileReader fr = new FileReader("src/main/resources/"+languageFile);
			BufferedReader br = new BufferedReader(fr); 
			String word;	words = new ArrayList<String>();
			while ((word = br.readLine()) != null) {
				words.add(word);
			}
			
			br.close();
			                    
			} catch (IOException e) {
				System.out.println("Errore nella lettura del file");
				e.printStackTrace();
			}
		
	}
	
	
	public List<RichWord> spellCheckText(List<String> inputTextList) {
		List<RichWord> result = new ArrayList<RichWord>();
		RichWord rw;
		
		for(String s: inputTextList) {
			if(words.contains(s)) {
				rw = new RichWord(s, true);
			} else {
				rw = new RichWord(s, false);
			}
			
			result.add(rw);
		}
		
		return result;
	}
	
	
	public List<RichWord> spellCheckTextLinear(List<String> inputTextList) {
		List<RichWord> result = new ArrayList<RichWord>();
	
		for(String s: inputTextList) {
			RichWord rw = null;
			for(String ss: words) {
				if(s.equals(ss)) 
					rw = new RichWord(s, true);
			}
			
			if(rw == null)
				rw = new RichWord(s, false);
			
			result.add(rw);
		}
		
		return result;
	}
	
	public List<RichWord> spellCheckTextDichotomic(List<String> inputTextList) {
		List<RichWord> result = new ArrayList<RichWord>();
		
		for(String s: inputTextList) {
			RichWord rw = null;
			int size = words.size()/2;
			int delta = words.size()/2;
			boolean flag = false; 
			
			while(delta != -1) {
				String prov = words.get(size);
				
				if(prov.compareTo(s) == 0) {
					rw = new RichWord(s, true);
					break;
				} else if(prov.compareTo(s) > 0) {
					delta = delta/2;
					size = size - delta;
				} else if(prov.compareTo(s) < 0) {
					delta = delta/2;
					size = size + delta;
				}
				
				if(delta == 0 && flag)
					delta = -1;
				if(delta == 0) {
					flag = true;
					if(prov.compareTo(s) > 0) {
						size--;
					} else if(prov.compareTo(s) < 0) {
						size++;
					}
				}
				
			}
			
			if(rw == null)
				rw = new RichWord(s, false);
			
			result.add(rw);
		}
		
		return result;
	}
}

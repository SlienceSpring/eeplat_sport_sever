package com.exedosoft.plat.lucene;

import java.io.File;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.StringField;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.index.IndexWriterConfig.OpenMode;
import org.apache.lucene.index.Term;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.apache.lucene.util.Version;

import com.exedosoft.plat.bo.BOInstance;
import com.exedosoft.plat.util.DOGlobals;

public class LuceneManager {

	public static synchronized void createIndex(BOInstance bi, boolean create) {

		try {

			System.out.println("Test LuceneManager");
			// make a new, empty document
			Document doc = new Document();

			Directory dir = FSDirectory.open(new File("d:\\index"));
			// :Post-Release-Update-Version.LUCENE_XY:
			Analyzer analyzer = new StandardAnalyzer(Version.LUCENE_47);
			IndexWriterConfig iwc = new IndexWriterConfig(Version.LUCENE_47,
					analyzer);

			if (create) {
				// Create a new index in the directory, removing any
				// previously indexed documents:
				iwc.setOpenMode(OpenMode.CREATE);
			} else {
				// Add new documents to an existing index:
				iwc.setOpenMode(OpenMode.CREATE_OR_APPEND);
			}

			// Optional: for better indexing performance, if you
			// are indexing many documents, increase the RAM
			// buffer. But if you do this, increase the max heap
			// size to the JVM (eg add -Xmx512m or -Xmx1g):
			//
			// iwc.setRAMBufferSizeMB(256.0);

			IndexWriter writer = new IndexWriter(dir, iwc);

			Field tableField = new StringField("table", bi.getBo().getSqlStr(),
					Field.Store.YES);
			doc.add(tableField);

			Field oField = new StringField("boject", bi.getBo().getSqlStr(),
					Field.Store.YES);
			doc.add(oField);

			Field idField = new StringField("instanceid", bi.getUid(),
					Field.Store.YES);
			doc.add(idField);

			if( bi.getValue("status")!=null){
				Field statusField = new StringField("status", bi.getValue("status"),
						Field.Store.YES);
				doc.add(statusField);
			}

			
			if (DOGlobals.getInstance().getSessoinContext().getUser() != null) {
				Field userIdField = new StringField("userid", DOGlobals
						.getInstance().getSessoinContext().getUser().getUid(),
						Field.Store.YES);
				doc.add(userIdField);

				Field userNameField = new StringField("userName", DOGlobals
						.getInstance().getSessoinContext().getUser().getName(),
						Field.Store.YES);
				doc.add(userNameField);
			}

			Field titleField = new StringField("title", bi.getName(),
					Field.Store.YES);
			doc.add(titleField);

			Field   contentField = new StringField("content", bi.getMap().values().toString(),
					Field.Store.NO);
			
			doc.add(contentField);

			
			
			if (writer.getConfig().getOpenMode() == OpenMode.CREATE) {
				// New index, so we just add the document (no old document can
				// be there):
				System.out.println("adding " + bi.getName());
				writer.addDocument(doc);
			} else {
				// Existing index (an old copy of this document may have been
				// indexed) so
				// we use updateDocument instead to replace the old one matching
				// the exact
				// path, if present:
				System.out.println("updating " + bi.getName());
				writer.updateDocument(new Term("instanceid", bi.getUid()), doc);
			}

		} catch (Exception e) {
		}
	}
	
	
	
	

}

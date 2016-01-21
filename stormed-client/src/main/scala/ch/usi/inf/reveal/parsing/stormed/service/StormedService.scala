package ch.usi.inf.reveal.parsing.stormed.service

import java.security.cert.X509Certificate
import org.json4s.native.Serialization.read
import org.json4s.native.Serialization.write
import ch.usi.inf.reveal.parsing.artifact.ArtifactSerializer
import javax.net.ssl._
import scalaj.http.Http
import java.security.SecureRandom
import scalaj.http.HttpOptions


object StormedService {
  implicit val formats = ArtifactSerializer.formats + ResponseSerializer  

  val trustManager = new X509TrustManager() {
    override def getAcceptedIssuers() = null
    override def checkClientTrusted(certs: Array[X509Certificate], authType: String): Unit = () 
    override def checkServerTrusted(certs: Array[X509Certificate], authType: String): Unit = ()
  }
  
  val trustAllCerts = Array[TrustManager](trustManager)
  val sc = SSLContext.getInstance("SSL")
  sc.init(null, trustAllCerts, new SecureRandom())
  HttpsURLConnection.setDefaultSSLSocketFactory(sc.getSocketFactory())
 
  HttpsURLConnection.setDefaultHostnameVerifier( new HostnameVerifier(){
    def verify(hostname: String, sslSession: javax.net.ssl.SSLSession ) = true
  })
  
  
  val url = "https://stormed.inf.usi.ch/parse"
  
  def parse(text: String, key: String) = {
    val request = ParseRequest(text, key)
    val jsonRequest = write(request)
    val bodyResponse = Http(url).postData(jsonRequest)
      .header("Content-Type", "application/json")
      .header("Charset", "UTF-8")
      .option(HttpOptions.connTimeout(50000)).option(HttpOptions.readTimeout(50000))
      .asString.body
    
    read[Response](bodyResponse)
  }
}
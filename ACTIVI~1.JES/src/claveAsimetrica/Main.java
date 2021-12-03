package claveAsimetrica;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.util.Scanner;

import javax.crypto.Cipher;
import javax.crypto.SealedObject;

public class Main {

	public static void main(String args[]) {

		
		try (Scanner sc = new Scanner(System.in)) {

			KeyPairGenerator generador = KeyPairGenerator.getInstance("RSA");
			KeyPair claves = generador.generateKeyPair();
			Cipher descifrador = Cipher.getInstance("RSA");

			boolean continuar = true;

			String mensajeOriginal = null;
			String mensajeCifrado = null;
			String mensajeDescifrado = null;
			do {

				escribirMenu(); // Invocamos al método que escribirá las opciones del menú

				int opcion = sc.nextInt();

				switch (opcion) { // Creamos un switch para cada una de las opciones del cliente.

				case 1:
					descifrador.init(Cipher.ENCRYPT_MODE, claves.getPublic());
					System.out.println("Introduzca la frase a encriptar:");
					sc.nextLine();
					mensajeOriginal = sc.nextLine(); 
					byte[] bytesMensajeOriginal = mensajeOriginal.getBytes(); // Pasamos el mensaje introducido a bytes
					byte[] bytesMensajeCifrado = descifrador.doFinal(bytesMensajeOriginal);// Ciframos el mensaje introducido
					mensajeCifrado = new String(bytesMensajeCifrado); //Convertimos en String el mensaje cifrado
					System.out.println("El mensaje ha sido encriptado.");
					descifrador.init(Cipher.DECRYPT_MODE, claves.getPrivate()); 
					byte[] bytesMensajeDescifrado = descifrador.doFinal(bytesMensajeCifrado); // Desciframos el mensaje
					mensajeDescifrado = new String(bytesMensajeDescifrado); //Convertimos en String el mensaje descifrado
					break;

				case 2:
					if (mensajeCifrado == null) {
						System.out.println("Por favor, primero utilice la opción 1 e introduzca un mensaje.");

					} else {
						System.out.println("El mensaje cifrado es: " + mensajeCifrado);
					}
					break;
				case 3:
					if (mensajeDescifrado == null) {
						System.out.println("Por favor, primero utilice la opción 1 e introduzca un mensaje.");

					} else {

						System.out.println("El mensaje descifrado es: " + mensajeDescifrado);
					}
					break;
				case 4:

					System.out.println("Introduzca la matricula:");
					sc.nextLine();
					String matricula = sc.nextLine();
					System.out.println("Introduzca la marca:");
					String marca = sc.nextLine();
					System.out.println("Introduzca el modelo:");
					String modelo = sc.nextLine();
					System.out.println("Introduzca el precio:");
					double precio = sc.nextDouble();

					Coche c1 = new Coche(matricula, marca, modelo, precio);
					descifrador.init(Cipher.DECRYPT_MODE, claves.getPublic()); 
					SealedObject so = new SealedObject(c1, descifrador);
					System.out.println("El siguiente coche ha sido encriptado:");
					
					System.out.println(c1);
					System.out.println("Coche cifrado: " + so.toString());
					descifrador.init(Cipher.DECRYPT_MODE, claves.getPrivate()); 
					break;
				case 5:
					continuar = false;
					break;
				default:
					System.out.println("Introduzca una opción válida:");
					;
				

				}

			} while (continuar);

		} catch (Exception gse) {
			System.out.println("Algo ha fallado.." + gse.getMessage());
			gse.printStackTrace();
		}

	}

	public static void escribirMenu() {
		System.out.println("Elija una de las siguientes opciones:");
		System.out.println("1. Encriptar frase:");
		System.out.println("2. Mostrar frase encriptada:");
		System.out.println("3. Desencriptar frase:");
		System.out.println("4. Encriptar coche:");
		System.out.println("5. Salir del programa");
	}
}

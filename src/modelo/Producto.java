package modelo;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.Blob;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.embed.swing.SwingFXUtils;
import javafx.scene.image.Image;
import javafx.scene.image.PixelFormat;
import javax.imageio.ImageIO;

/**
 *
 * @author Mayra
 */
public class Producto {

    private int id_producto;
    private String nombre;
    private String descripcion;
    private Date fecha_aniade;
    private Date fecha_borra;
    private Date fecha_mod;
    private int usuario_aniade;
    private int usuario_borra;
    private int usuario_mod;
    private double precio;
    private double cantidad;
    private boolean eliminado;
    private Blob imagen;
    private Image imagenProducto;

    public Producto(int id_producto, String nombre, String descripcion, Date fecha_aniade, Date fecha_borra, Date fecha_mod, int usuario_aniade, int usuario_borra, int usuario_mod, double precio, double cantidad, boolean eliminado, Blob imagen, Image imagenProducto) {
        this.id_producto = id_producto;
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.fecha_aniade = fecha_aniade;
        this.fecha_borra = fecha_borra;
        this.fecha_mod = fecha_mod;
        this.usuario_aniade = usuario_aniade;
        this.usuario_borra = usuario_borra;
        this.usuario_mod = usuario_mod;
        this.precio = precio;
        this.cantidad = cantidad;
        this.eliminado = eliminado;
        this.imagen = imagen;
        this.imagenProducto = imagenProducto;
    }

    public Producto(int id_producto, String nombre, String descripcion, double precio, double cantidad, Image imagen) {
        this.id_producto = id_producto;
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.precio = precio;
        this.cantidad = cantidad;
        this.imagenProducto = imagen;
    }

    public Producto(String nombre, String descripcion, Double precio, Double cantidad, Image imagenRecogida) {
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.precio = precio;
        this.cantidad = cantidad;
        this.imagenProducto =  imagenRecogida;

    }

    public int getId_producto() {
        return id_producto;
    }

    public void setId_producto(int id_producto) {
        this.id_producto = id_producto;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public Date getFecha_aniade() {
        return fecha_aniade;
    }

    public void setFecha_aniade(Date fecha_aniade) {
        this.fecha_aniade = fecha_aniade;
    }

    public Date getFecha_borra() {
        return fecha_borra;
    }

    public void setFecha_borra(Date fecha_borra) {
        this.fecha_borra = fecha_borra;
    }

    public Date getFecha_mod() {
        return fecha_mod;
    }

    public void setFecha_mod(Date fecha_mod) {
        this.fecha_mod = fecha_mod;
    }

    public int getUsuario_aniade() {
        return usuario_aniade;
    }

    public void setUsuario_aniade(int usuario_aniade) {
        this.usuario_aniade = usuario_aniade;
    }

    public int getUsuario_borra() {
        return usuario_borra;
    }

    public void setUsuario_borra(int usuario_borra) {
        this.usuario_borra = usuario_borra;
    }

    public int getUsuario_mod() {
        return usuario_mod;
    }

    public void setUsuario_mod(int usuario_mod) {
        this.usuario_mod = usuario_mod;
    }

    public double getPrecio() {
        return precio;
    }

    public void setPrecio(double precio) {
        this.precio = precio;
    }

    public double getCantidad() {
        return cantidad;
    }

    public void setCantidad(double cantidad) {
        this.cantidad = cantidad;
    }

    public boolean isEliminado() {
        return eliminado;
    }

    public void setEliminado(boolean eliminado) {
        this.eliminado = eliminado;
    }

    public Blob getImagen() {
        return imagen;
    }

    public void setImagen(Blob imagen) {
        this.imagen = imagen;
    }

    public Image getImagenProducto() {
        return imagenProducto;
    }

    public void setImagenProducto(Image imagenProducto) {
        this.imagenProducto = imagenProducto;
    }

    //metodo para obtener todos los productos que no tienen el eliminado a 1
    public static ObservableList obtenerProductos() {
        ObservableList<Producto> listaProductos = FXCollections.observableArrayList();

        try ( ResultSet result = Conexion.obtenerConexion().createStatement().executeQuery("SELECT * FROM producto WHERE eliminado = 0")) {

            while (result.next()) {
                byte byteImage[];
                int id = result.getInt("id_producto");
                String nombre = result.getString("nombre");
                String descripcion = result.getString("descripcion");
                double precio = result.getDouble("precio");
                double cantidad = result.getDouble("cantidad");
                Blob imagen = result.getBlob("imagen");
                byteImage = imagen.getBytes(1, (int) imagen.length());
                //crear el Image y mostrarlo en el ImageView
                Image img = new Image(new ByteArrayInputStream(byteImage));
                listaProductos.add(new Producto(id, nombre, descripcion, precio, cantidad, img));
            }
        } catch (SQLException ex) {
            System.out.println("Ocurrió un error al obtener los productos");
            System.out.println("Mensaje del error " + ex.getMessage());
            System.out.println("Detalles del error ");
            ex.printStackTrace();
        }
        return listaProductos;
    }

    //metodo para obtener la id de los productos por nombre
    public static int obtenerId(String producto) {
        int id_producto = 0;

        try {
            try ( ResultSet result = Conexion.obtenerConexion().createStatement().executeQuery(
                    "SELECT id_prodcuto FROM producto WHERE nombre = '" + producto + "'")) {
                while (result.next()) {
                    id_producto = result.getInt("id_producto");

                }
            }

        } catch (SQLException ex) {
            System.out.println("Ocurrió un error al obtener el id del producto");
            System.out.println("Mensaje del error " + ex.getMessage());
            System.out.println("Detalles del error ");
            ex.printStackTrace();
        }

        return id_producto;
    }
    //metodo insertar producto en la tabla

    public static boolean insertarProducto(Producto producto) {
        try {
//INSERT INTO `producto` (`id_producto`, `nombre`, `descripcion`, `precio`, `cantidad`, `imagen`, `fecha_aniade`
            //, `fecha_borra`, `fecha_mod`, `usuario_aniade`, `usuario_borra`, `usuario_mod`, `eliminado`) VALUES (NULL, 
            //'imagen2', 'descripción de la imagen 2', '0.50', '200', 0x89504e470d0a1a0a0000000d49484452000001f4000001f40806000000cbd6df8a000000017352474200aece1ce9000000097048597300000ec400000ec401952b0e1b0000047569545874584d4c3a636f6d2e61646f62652e786d7000000000003c3f787061636b657420626567696e3d27efbbbf272069643d2757354d304d7043656869487a7265537a4e54637a6b633964273f3e0a3c783a786d706d65746120786d6c6e733a783d2761646f62653a6e733a6d6574612f273e0a3c7264663a52444620786d6c6e733a7264663d27687474703a2f2f7777772e77332e6f72672f313939392f30322f32322d7264662d73796e7461782d6e7323273e0a0a203c7264663a4465736372697074696f6e207264663a61626f75743d27270a2020786d6c6e733a4174747269623d27687474703a2f2f6e732e6174747269627574696f6e2e636f6d2f6164732f312e302f273e0a20203c4174747269623a4164733e0a2020203c7264663a5365713e0a202020203c7264663a6c69207264663a7061727365547970653d275265736f75726365273e0a20202020203c4174747269623a437265617465643e323032322d30392d32373c2f4174747269623a437265617465643e0a20202020203c4174747269623a45787449643e38326339616534352d353963392d346633332d613161352d3530323166326438663364383c2f4174747269623a45787449643e0a20202020203c4174747269623a466249643e3532353236353931343137393538303c2f4174747269623a466249643e0a20202020203c4174747269623a546f756368547970653e323c2f4174747269623a546f756368547970653e0a202020203c2f7264663a6c693e0a2020203c2f7264663a5365713e0a20203c2f4174747269623a4164733e0a203c2f7264663a4465736372697074696f6e3e0a0a203c7264663a4465736372697074696f6e207264663a61626f75743d27270a2020786d6c6e733a64633d27687474703a2f2f7075726c2e6f72672f64632f656c656d656e74732f312e312f273e0a20203c64633a7469746c653e0a2020203c7264663a416c743e0a202020203c7264663a6c6920786d6c3a6c616e673d27782d64656661756c74273e506c61737469636f7320436f72616c202d20343c2f7264663a6c693e0a2020203c2f7264663a416c743e0a20203c2f64633a7469746c653e0a203c2f7264663a4465736372697074696f6e3e0a0a203c7264663a4465736372697074696f6e207264663a61626f75743d27270a2020786d6c6e733a7064663d27687474703a2f2f6e732e61646f62652e636f6d2f7064662f312e332f273e0a20203c7064663a417574686f723e6d6179726120656e72697175657a3c2f7064663a417574686f723e0a203c2f7264663a4465736372697074696f6e3e0a0a203c7264663a4465736372697074696f6e207264663a61626f75743d27270a2020786d6c6e733a786d703d27687474703a2f2f6e732e61646f62652e636f6d2f7861702f312e302f273e0a20203c786d703a43726561746f72546f6f6c3e43616e76613c2f786d703a43726561746f72546f6f6c3e0a203c2f7264663a4465736372697074696f6e3e0a3c2f7264663a5244463e0a3c2f783a786d706d6574613e0a3c3f787061636b657420656e643d2772273f3e2ccc32de00001c7b49444154789ceddd79b45e757deff1cf73323113204c09934c121410100451064544948be2ac556beb506bd5baae1df456db55f5b6aeeb5487db2b75c2b14b541c5150145140c5c8203328040804082109899973ee1f5b1601329c793fcff779bdd63aeb847376cef3551ecefbd9fbd9fbb73b67be7d703000404f1b687b000060f4041d000a1074002840d001a00041078002041d000a1074002840d001a00041078002041d000a1074002840d001a00041078002041d000a1074002840d001a00041078002041d000a1074002840d001a00041078002041d000a1074002840d001a00041078002041d000a1074002840d001a00041078002041d000a1074002840d001a00041078002041d000a1074002840d001a00041078002041d000a1074002840d001a00041078002041d000a1074002840d001a00041078002041d000a1074002840d001a00041078002041d000a1074002840d001a00041078002041d000a1074002840d001a00041078002041d000a1074002840d001a00041078002041d000a1074002840d001a00041078002041d000a1074002840d001a00041078002041d000a1074002840d001a00041078002041d000a1074002840d001a00041078002041d000a1074002840d001a00041078002041d000a1074002840d001a00041078002041d000a1074002840d001a00041078002041d000a1074002840d001a00041078002041d000a1074002840d001a00041078002041d000a1074002840d001a00041078002041d000a1074002840d001a00041078002041d000a1074002840d001a00041078002041d000a1074002840d001a00041078002041d000a1074002840d001a00041078002041d000a1074002840d001a00041078002041d000a1074002840d001a00041078002041d000a1074002840d001a00041078002041d000a1074002840d001a00041078002041d000a1074002840d001a00041078002041d000a1074002840d001a00041078002041d000a1074002840d001a00041078002041d000a1074002840d001a00041078002041d000a1074002840d001a00041078002041d000a1074002840d001a00041078002041d000a1074002840d001a00041078002041d000a1074002840d001a00041078002041d000a98dcf600c0e86db545b2d596c9d65b367f9eb48997ea6bd6262b57258383c98a55c98a95c9f295cdd780de25e8d02366ed92ec332bd963d764b79d929d774876de3199317dec1e63e1e264d183c9fcfb935be725b7ded97c5ef4e0d83d06303e3a67be7d70b0ed2180874d9d923c6e5613efc7cd4cf6dabdf9e7a953da9b69d183c91ffe14f7873ee62f686f1ee0b1041dbac08edb274f39a4f978c27ec940979fdd323898dc343799735df29b6b93b977b73d1120e8d092dd6624c71cda44fc80bd924ea7ed89466ec1a264ceb5c96fae4baebea9799f1e9858820e1368d62e4dc08f392cd96f8fb6a7191fcb96273ff975f2a3cb9279f7b63d0df40f418771d6e924871d983cebd8e4a8276efa0cf46a7e7773f2c34b925f5f93ac5bd7f634509bb3dc619c0c0c24271c99bce099cd9e793f3ae480e663c1a2668ffdc7bf74c63c8c177be830c6a64c4e4e3a3a79fe49c9ae3bb53d4d7759b336f9c12f92af5f902c5ddef634508ba0c31899323939f998e4f9cf18db6bc32b5aba3cf9c68f92f37eee043a182b820ea3346d6af2eca726679c944cdfb6ed697acbbd0b932f9f975c724573291c3072820ea370f841c96b9fdfbfef918f95dbef4e3ef7ede692376064041d4660d62e4dc80f3fa8ed496af9c56f9bb03b710e86cf59ee300cd3a626673eb339bc3ec57f3d63ee694724471c9c7cf9fbc9f9973a0c0fc3610f1d86e8a98725af39c3096f13e50f7726fff9b5e633b079820e9bd1e934d792bffcd4ee5f63bd9ac1c1e632b72f7ddfed5d61731c34844dd866abe46daf4c8e98ddf624fda9d3494e7b7a72f07ec987bf602959d814fb1bb011b3f74d3ef40e31ef06fbcc4cfefd6f93271fdcf624d0bd1c7287471918485e7c4af2a2931d62ef368383c9393f4abe76be13e6e0d11c7287f5cc989efced9f357be7749f4e2779c929c9be7b24fff1a5e48f2bda9e08ba87fd0ff893edb749def57a31ef054f3eb83904bfcfccb62781ee21e89064b719c9ff7e6bb2f7ee6d4fc250cdda2579f71badd2070f1174fadeee3b3731df6d46db93305cd3b74dfef5cda20e89a0d3e7f69e99bcff2dcde1767a93a84343d0e95b07ee9dbcf7cd625e81a883a0d3a7f6dd23f95faf4fb6deb2ed49182ba24ebf1374facebe7b24fffc57cd2a70d4327ddbe47d6f713e04fd49d0e92b625edf765b27ef7e43f319fa89a0d33776dc3e79e75f8a793fd86d46f2ced72553a7b43d094c1c41a72f6cb545f24f6f68a24e7f3870ef66d5bf4ea7ed496062083ae5753ac9dffdb94563fad1530e49fefc8cb6a7808921e894f7925392430f6c7b0adaf2bce39b0fa84ed029ed89fb37774ea3bfbdeaf4e6103c5426e894357ddbe4edaff21e2ac9e449cdfbe9d3a6b63d098c1f41a7a44e27f99faf6ea20e49b2eb4ec9eb5fd8f614307e049d925e724a72f07e6d4f41b739e9a8e4b8c3db9e02c687a053ce91077bdf9c8d7bc38b9abd75a846d029658b69c91b5fe47d73366e9b2d5d9f4e4d824e292f7f4eb2d3f4b6a7a0db1db877f2ac63db9e02c696a053c67e7b24a73daded29e8152f3f35d9cead732944d029e38d2f49063ca319a2edb669a20e55f8f54709c71ddeeca1c3709c7c4cb2e7ae6d4f016343d0e979532627af7e5edb53d08b0606acf54e1d824ecf7bde09c98c1dda9e825ef5a48392a39fd8f614307a824e4fdb6a8be4cc67b63d05bdee2f5fd01ce9815e26e8f4b4d34f68a20ea3316387e4a4a3db9e024647d0e959d3b76d0eb7c35838fd846492df88f4304f5f7ad60b9e69ef9cb13373e7e498c3da9e02464ed0e94953a724271ed5f6145473c6899684a577093a3de9c4a39a35b9612cedb76772d8e3db9e024646d0e9399d4ef2bce3db9e82aa5c3541af12747ace618f4f66edd2f61454f584fd9a9bb740af11747aceb39fdaf604547792f333e841824e4fd976ebe4c883db9e82ea9e7268326952db53c0f0581b899e72f231ae15ee66abd72437de96dc3e3fb9ebde64c9b264f98ae67b5b4e6b5e90edb95bb2cfcc64ffbd92c99b89e6aad5c9bf7d3a194cf2d657243b6e3feeff139224db6f931c393bf9f53513f3783016049d9ef2b4c327f6f16eb93d39f727e3f7f33b49062635619b3ca989ded65b3567f0ef343dd965c764d79dbafb7afb75eb92cbaf4d2efc55f2bb9b9b083fda402799322559b9eae1af4d9d923c7e9fe489fb371f07eed36cb7becbaf4daebeb9f9f38a559950271d2de8f41641a767ccdaa5d9b39b48f72f4e7e79f5c43ee686ecba63b2ef1ec9210726871fd484be6debd6253fbd3c39e782e4be071ef9bdedb7499e7e64b397bbcfcce6dee30ffd9d7b172637df9e5c724532e7bae64540926cb775f2944392d9fb36db5f734bf2c35f3cf2f126d291b3932da6252b564eece3c24875ce7cfbe060db43c050bcf4d9c94b9eddf6140f7bc53f3e728f73530e3920f997373df26beb0693d5ab9bc3d44b96258b9624f72d4a6e9e9b5c7265b264e9c67fdee3f7499e754c13cdcd1db61e0fd7fd3e39eb1bc91df31ff9f5a95392979e9a9cf6b4e6cf9b73c7fce4d3df6ce2bd391ffdfbe670fd44fac4579b172dd00beca1d3133a9de48427b73dc5d81ae824d3a6361fdb6cd52c3d9a24271c99bcfaf4e49c1f25dffcf186ffee8db7351fe75c90bceaf4e4d8095ab274d5eae40bdf4d7ef08bc77e6ff719c93b5f37bc4b0af7dc2df9e737255f3bbff9dfb229eb5ad8f538e96841a77738bd889e30637af35e72bf983a2579e569c99b5fb6e9edee59987cf0ece4c35f1cfff798ef7b20f9c7ffd870cc77de2179efdf8c6c7d80814ef2b25393bf78fea6b79be843ee4972f0becd897cd00b049d9e70f8ecb62768c7338e4e9e7ec4e6b7bbe48ae49f3e9e2c7a707ce6b8637ef20f1f49e6def5d8ef0d0c24ef784db2c376a37b8ce71ebfe955dada7873b0d349663f6ee21f174642d0e909871fd4f604ed79c1109722bd755ef2beb392e5637c12d7bc7b93f7fcdf64f146ded33fe9a8e612b4b1f08ad392430fd8f0f7da38e49e244fd8bf9dc785e11274bade94c9cd654dfd6aefdd877ed8f7d679cd49666365d183c97b3fb5e913f44e1fc37bd2773ac95b5fb9e1cbf4da38e49ef4f7738fde22e874bd59bb74f775d81361d7615ca676d1e5c94d7347ff98abd7348bba3cfa92b4f5edbbc7d89f79bec376c92b9ffbd8afb7753dce705e50419b049dae670f29993eccf7a7cffbf9e81ff30bdf4d6eb963d3db1c314ee7369cf2d4c7be50682be8de47a757083a5dcf7b98cda56dc371c50da30be095370ced45c141e314ba814ef2e2673df26b6d1d724f3c07e90d824e57eb749a4b87fadda39744dd9ca57f4c6edbc019e943b1666df25f437c1f7eefdd47f6184371ec931eb9225e5b27c5259e83f40641a7ab6dbf4db3e80ac377f308df47ffdec5c9fc059bdf6e8ba9e37bb394814e72e27a8b09b5b9a6e56e339acbf3a09b798ad2d5f699d5f604bd6b24d7a4af5a9d9c7be1d0b6dd7902d6933f71bdfb92b779c87dab2d929d26e84e6f3052824e577b9ca08fd8c6ae1bdf948b2e6f0ed70fc544dcca74d79d9a25659376f7d093eeb8210e6c8aa0d3d546b2942823f7fd619c1d3f516f851c7260f3b9cdf7d09364d6aeed3e3e6c8e9bb3d0d5ec1535d68ee070f370af9dbeedaee4ce7b86befdd65b0eefe78fd4192736abc7edbbc7c43cdec6787149b71374ba9aa03796af18fedf19eedaea975d35bceda74cd06f8fdd66341f6df35ca4db39e44ed7ea749c88f49091accfbeff9ec3dbfed7d70c6ffb497df6dbc31e3addaecffe93a4976cbda54b851e32dca0efbcc3f00e512f5fd9dc516d38faeddf8da0d3edfaec3f497ac996d3da9ea07bdcbf6878db9f7a5c738463a86ebdb3fdb3c87bc17057ec838924e874ada953da9ea03bdc73ffd02f254b9219d393d39e3ebcc7f8c39dc3dbbe5f0d77c53e9848824ed712f4c625570e7ddb4e2779db2b87ffffdd7dc33c02d0af264d6a7b02d83841a76b097af2c715c9772e1afaf67ffdd2e4e0fd46f038cb87ff77fad16441a78bb96c8daed5ef415fb336f9d0d9c983cb36bfed96d39237bd2439eef0913dd632411f9289ba540f46c2d393aed56f9745adefaefb928f7f25b96908375879d241c9ebcf1cddb5da2b568dfceff6937e3bb39fde22e874ad7e79bf72f59a64c1a2e4defb9b905f756332e7ba4d2f753a7952f2944393e71c97cc1e835b7b4ef59b6048faf94526ddcf7fc674ad4abf3cafff43f217ef79e4d756ad4e56ae1eda5dc4260d24fbed993c71ffe409fb27b31f37b697506de112c12171c89d6ee6e949d71ac9fae5dd6aea9487ef1af6189d660f79dab4e61ee353a734f781df798764979d9a254777de617c63b2dd36e3f7b3818921e874ad55abdb9e60ececb767f22f6f6a7b8a8ddb6da7b627e80d6ddff10d36a5d0414daa59bbb6ed09fac7484ea81bca5b05d5784ed2cd049dae55e9907bb73b60afe1ff9d7efcf7b37a4ddb13c0c6093a5dabd221f76eb7edd6c9debb0fefefac9ec07f3fdffd59f2a97392eb7e3f718fb921824e371374ba96a04facc31e3fbced978de01eed23f59d8b920b2e4bee5e30718fb9216b1c72a78b093a5dab1f0fe9b6e984270f6ffb65c3b861cc682c7a3059b8b8f973dbab077a0f9d6e26e8742d873727d63e3393bd670e7dfb854bc66f96f5dd78dbc37f6e3de85e64d2c5049dae359435cc195ba71f3ff46d877b8ff691bafaa687ffdc66d057acf42293ee26e874ad55ab9bbb8d31714e78723273e7a16dbb6c79b278e9f8ce9324bfbdfee13fb719f4f9f72783ae43a78b093a5dedde856d4fd05f060692573c77e8dbcfbd7bfc66499a9bd3acff1c6873e955cf45ba9da0d3d5fc129d78c71e9a1cf5c4a16d7bc31fc67796f37efec87f9ed6e21ebae722dd4ed0e96af3ee697b82fef4572f4eb6d96af3db5d75e3f8cdb0707172d9558ffc5a9b87dce7dddbde63c350083a5dcd2fd1764cdf36f99b97279dcea6b7bbe1b6f1db73fdca798fbdee7b4a9b41f7e2922e27e874358739db73d41392573d6ff3db9d7fc9d83ff64d73938b7ef3d8afb7b987eeb948b71374badafcfbdb9ea0bf9d715272cab19bdee687978eedd9eecb96271ff9e286cf286f33e8f73dd0de63c350083a5dedfe457e91b6ed0d2f4a9ef3b48d7f7fc5cae433e78ecd63ad599b7cf88b1bdf1b9edad259eed7b6bc863c0c85a0d3f5aeb9a5ed09fa5ba793bceecce485276f7c9b4bae48cebf74748fb36a75f281cf2657deb0e1ef4f1a682eab6bc3b59e83f40041a7ebf965da1d5e715af2b6576efcb0f7a7bf91fc6ccec87ef63df727effad823179179b4360fb77b51492f68719906189a9be6b63d010f39fec864ffbd924f7eb539c37d7deb06938f7d39b96d5ef2f2e70c2dc06bd626dfbb3839e7fc64c5aa87bf3e6572327952b27ce57a5f6b29e883839e83f40641a7ebdd755fb3aefbb65b4ffc63df727b72ee4f36fcbde1dc0f7ceeddc9fff9fc86bff7d72f4db6de72f8b3b565e6cec9fbde92fcec37c9577ef0d835ddbf735172e995c9e927362f00b6dbc0bfb7bbee6bb6f9e125c9037fbac9cbd429c9718727cf383a39689fe650ff8f7f959c754ef362a1ad4565e6de6d0d777a83a0d3f5060793eb6f4d8e1ee2ea6563e9fec5c92faf1efdcf59b274e33fe7f52f1cfdcf9f689d4e72e251c9d38e482e9ed35cba76cb1d0f7f7fc1a2e473df4acefe76b2d7eec9cc5d9a602f59da0472fd17017bcf6c227ed2518f7d61f3ac6392bbee6d5e24b4b5ecabc3edf48ace996f77bb01badff38e4f5efbfcb6a76053e62f48aebcb1b9dde91df39bab13963eea9ee953a7243b6c9bccda3599bd6ff3226d8f5ddb9977a83ef0d9e4d7d7b43d056c9e3d747ac2153724af6d7b083669b719c9a93392538f7bf86b8383c9cad5cde7c993dabdb9ca48ac5d9b5c7d73db53c0d038cb9d9e30efdee4b6bbda9e82e1ea74922da6265b4eebbd9827c99ceb9bebeca117083a3de31757b43d01fdc6738e5e22e8f48ccbaedaf072a0301e56ac4ce65cd7f6143074824ecf98bf20b9f9f6b6a7a05fccb9cee1767a8ba0d3532e71089409e2703bbd46d0e9293f9b93ac5dd7f61454b76091c3edf41e41a7a73cb82cf9e5556d4f41753ff995178ef41e41a7e77cefe2b627a0b2b5eb920b7fd5f614307c824ecfb969ae6bd2193f73ae6b0eb943af11747ad268efbd0d1bf37d4780e851824e4fbae8f264e1e2b6a7a09a9be6ba190bbd4bd0e949ab5627dffe69db5350cdb917b63d018c9ca0d3b32eb82c59babced29a8e2d679c9e5d7b63d058c9ca0d3b356ad4ecef37e2763e46be75b5a98de26e8f4b4ef5f6c2f9dd19bbfc0de39bd4fd0e9694b97db4b67f4cefe8ebd737a9fa0d3f3be796173bf7418892b6f487e7d4ddb53c0e8093a3d6ff59ae4b3df6a7b0a7ad1ba759e3bd421e894602f8b91f8f12f1ddda10e41a78ccf7c3359eefed50cd19265c9577fd0f6143076049d32162c4abef1e3b6a7a0577ce97b4dd4a10a41a7946ffd24b9e1d6b6a7a0db5d79833baa518fa053cae060f2f1af262b57b53d09dd6ae9f2e413ffddf61430f6049d72e62f483eff9db6a7a05b9df5f5e481256d4f01634fd029e9824b93df5edff614749b4bae683ea02241a7acfffc9a43ef3cecfe45c9a7cf6d7b0a183f824e590b17279ff8aa253d49feb82279ffa793254bdb9e04c68fa053daa557255f3eafed2968d3e060f2c1b393b977b53d098c2f41a7bc732f6cc24e7ffaf279c95537b63d058c3f41a72f7cfc2bae4fef473fff6df3820efa81a0d31756ad4e3ef0b9663539fac3ef6e6e5ec841bf1074fac692a5c9fbce6a4e96a3b69b6f4f3ef0d964edbab627818923e8f4953be627fffaa9e4416b7897357f41f2feff72a31efa8fa0d377ee989fbccf2ffc92162e4ede7b96176cf42741a72fdd727bf2ef9f4956af697b12c6cae2a5cd0bb5f90bda9e04da21e8f4ad6b6e493ef87951af60fe82e45d1f73ad39fd4dd0e96bbfb92ef9bb0fdbabeb6573ef6e62eedf21fd4ed0e97b77cc4fdef9b1e4f777b43d09c375cd2dc9bb3fd11c6e877e27e890e692b6777fd21dda7ac9cf7fdb5c86b86c79db9340771074f89395ab927ffb4cf2bd9fb53d099bb26e5d72f677928f7ec9f90fb0bec96d0f00dd64ddbae473df4e7e7f67f2e697259327b53d11eb7b7059f2a12f34abc0018f24e8b00117cf49eeb93f79c76b921db76f7b1a9264debdc97b3f95dcf740db93407772c81d36e2c6db927ff868f39976fde8b2e4ef3f22e6b029820e9bb07071f29e4f367bec4cbc65cb9bf31afedf39c90a2bfbc12675ce7cfbe060db43402fd87bf7e67df5fdf66c7b92fe70f59fee96e6663a3034820ec3d0e924a73c35f9b3e7265b6dd1f63435ad5899fcf7f9c9772f6a7b12e82d820e23b0c376c96bfe47f2f423da9ea48ec1c1e4e2df26677fdb42313012820ea3b0d7eec96bcf480e3db0ed497adbcd7393cf7cabf90c8c8ca0c31838f4c026ec7beddef624bd65c1a2e42bdf4f7ee6a4431835418731d2e924271d95bcfc39ae5ddf9c050f245fff51f2d3cb93356bdb9e066a107418630303cd7beb2f3c3999b54bdbd37497079624dfbc30b9e0522187b126e8304e3a9de4d84393173e2bd96766dbd3b4ebd679c90f7ed1dc5065d5eab6a7819a041d26c0618f4f5e747272f07e6d4f327156ac6c027efea54dd081f1652d77980057ddd87c6cb355f2d42725cf383a3960afb6a71a1f57de98fcea77cdea7a56778389630f1d5ab2e7aec9339e921c7f64327ddbb6a719b9254b9339d727bfb936b9e286e636b4c0c41374e8023b6e9f1c313b39fca0e612b86e5e856ee5aae68635d7fe3eb9e696e4865bdb9e0848041dbacea449c9ecc735713f7c76b3867c9b163d98dc362fb9fe5601876e26e8d003a64d6d6e0ab3ef1ec9ee3392193b2433a6379fb7d972f43f7fe1e266b9d5079634f71d9f774f72c73dc99df393a5cb47fff381f1e7a438e8012b5725d7fdbef978b4499392adb748b698964c9ed4fcf3439f270d349f073a0f6fbf666df3f3ee5b98ac5c9dac5e3371ff3b80f123e8d0e3d6ae4d962c6b3e80fe35d0f60000c0e8093a001420e8005080a0034001820e0005083a001420e8005080a0034001820e0005083a001420e8005080a0034001820e0005083a001420e8005080a0034001820e0005083a001420e8005080a0034001820e0005083a001420e8005080a0034001820e0005083a001420e8005080a0034001820e0005083a001420e8005080a0034001820e0005083a001420e8005080a0034001820e0005083a001420e8005080a0034001820e0005083a001420e8005080a0034001820e0005083a001420e8005080a0034001820e0005083a001420e8005080a0034001820e0005083a001420e8005080a0034001820e0005083a001420e8005080a0034001820e0005083a001420e8005080a0034001820e0005083a001420e8005080a0034001820e0005083a001420e8005080a0034001820e0005083a001420e8005080a0034001820e0005083a001420e8005080a0034001820e0005083a001420e8005080a0034001820e0005083a001420e8005080a0034001820e0005083a001420e8005080a0034001820e0005083a001420e8005080a0034001820e0005083a001420e8005080a0034001820e0005083a001420e8005080a0034001820e0005083a001420e8005080a0034001820e0005083a001420e8005080a0034001820e0005083a001420e8005080a0034001820e0005083a001420e8005080a0034001820e0005083a001420e8005080a0034001820e0005083a001420e8005080a0034001820e0005083a001420e8005080a0034001820e0005083a001420e8005080a0034001820e0005083a001420e8005080a0034001820e0005083a001420e8005080a0034001820e0005083a001420e8005080a0034001820e0005083a001420e8005080a0034001820e0005083a001420e8005080a0034001820e0005083a001420e8005080a0034001820e0005083a001420e8005080a0034001820e0005083a0014f0ff01e8e39efc0b4322de0000000049454e44ae426082, '2022-10-19 15:00:00', NULL, NULL, '7', NULL, NULL, '0');
            //Statement stmt = Conexion.obtenerConexion().createStatement();
            Image imagen = producto.getImagenProducto();
            BufferedImage bi = new BufferedImage((int) imagen.getWidth(), (int) imagen.getHeight(), BufferedImage.TYPE_INT_ARGB);
            ByteArrayOutputStream baos = null;
            try {
                baos = new ByteArrayOutputStream();
                ImageIO.write(bi, "png", baos);
            } catch (IOException ex) {
                Logger.getLogger(Producto.class.getName()).log(Level.SEVERE, null, ex);
            } finally {
                try {
                    baos.close();
                } catch (Exception e) {
                }
            }
            ByteArrayInputStream bais = new ByteArrayInputStream(baos.toByteArray());
            String sql = "INSERT INTO producto (nombre, descripcion, precio, cantidad, imagen,"
                    + "fecha_aniade, usuario_aniade) "
                    + "VALUES (?,?,?,?,?,?,?)";
            PreparedStatement ps = Conexion.obtenerConexion().prepareStatement(sql);
//            String sql = "INSERT INTO producto (id_producto, nombre, descripcion, precio, cantidad, imagen, "
//                    + "fecha_aniade, fecha_borra, fecha_mod, usuario_aniade, usuario_borra, usuario_mod, eliminado) "
//                    + "VALUES (NULL, '" + nombre + "', '" + descripcion + "', '" + precio
//                    + "', '" + cantidad + "', '" + imagen + "', '"
//                    + "', '" + Timestamp.valueOf(LocalDateTime.now()) + "', NULL, NULL, DEFAULT, " + Global.usuarioLogueadoId
//                    + ", NULL, NULL)";
            ps.setString(1, producto.getNombre());
            ps.setString(2, producto.getDescripcion());
            ps.setDouble(3, producto.getPrecio());
            ps.setDouble(4, producto.getCantidad());
            ps.setBlob(5, bais);
            ps.setTimestamp(6, Timestamp.valueOf(LocalDateTime.now()));
            ps.setInt(7, Global.usuarioLogueadoId);

            ps.execute(sql);
            return true;

        } catch (SQLException ex) {
            System.out.println("Ocurrió un error al insertar el producto");
            System.out.println("Mensaje del error " + ex.getMessage());
            System.out.println("Detalles del error ");
            ex.printStackTrace();
            return false;
        }

    }

}

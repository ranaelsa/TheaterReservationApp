import Footer from "@/components/Footer";
import Login from "@/components/Login";

const LoginPage = () => {
  return (
  <div className="flex flex-col min-h-screen">
    <div className="flex-grow">
      <Login />
    </div>
  <Footer />
  </div>
  );
}

export default LoginPage;
import { Route, Routes, Navigate } from "react-router-dom";

import Sidebar from "./components/common/Sidebar";
import { AuthProvider } from "./context/AuthContext";
import { useAuth } from "./hooks/useAuth";
import AuthPage from "./pages/AuthPage";

import OverviewPage from "./pages/OverviewPage";
import ProductsPage from "./pages/ProductsPage";
import UsersPage from "./pages/UsersPage";
import SalesPage from "./pages/SalesPage";
import OrdersPage from "./pages/OrdersPage";
import AnalyticsPage from "./pages/AnalyticsPage";
import SettingsPage from "./pages/SettingsPage";


function AppContent() {
	const { isAuthenticated } = useAuth();

	if (!isAuthenticated) {
		return (
			<Routes>
				<Route path="/auth" element={<AuthPage />} />
				<Route path="*" element={<Navigate to="/auth" replace />} />
			</Routes>
		);
	}

	return (
		<div className='flex h-screen bg-gray-900 text-gray-100 overflow-hidden'>
			{/* BG */}
			<div className='fixed inset-0 z-0'>
				<div className='absolute inset-0 bg-gradient-to-br from-gray-900 via-gray-800 to-gray-900 opacity-80' />
				<div className='absolute inset-0 backdrop-blur-sm' />
			</div>

			<Sidebar />
			<Routes>
				<Route path='/' element={<OverviewPage />} />
				<Route path='/products' element={<ProductsPage />} />
				<Route path='/users' element={<UsersPage />} />
				<Route path='/sales' element={<SalesPage />} />
				<Route path='/orders' element={<OrdersPage />} />
				<Route path='/analytics' element={<AnalyticsPage />} />
				<Route path='/settings' element={<SettingsPage />} />
				<Route path="/auth" element={<Navigate to="/" replace />} />
			</Routes>
		</div>
	);
}

function App() {
	return (
		<AuthProvider>
			<AppContent />
		</AuthProvider>
	);
}

export default App;
import { motion } from "framer-motion";
import { Edit, Search, Trash2, AlertCircle } from "lucide-react";
import { useState, useEffect } from "react";
import apiService from "../../services/api";

const ProductsTable = () => {
	const [searchTerm, setSearchTerm] = useState("");
	const [products, setProducts] = useState([]);
	const [filteredProducts, setFilteredProducts] = useState([]);
	const [loading, setLoading] = useState(true);
	const [error, setError] = useState("");

	useEffect(() => {
		fetchProducts();
	}, []);

	useEffect(() => {
		const filtered = products.filter(
			(product) =>
				product.name.toLowerCase().includes(searchTerm.toLowerCase()) ||
				product.category.toLowerCase().includes(searchTerm.toLowerCase())
		);
		setFilteredProducts(filtered);
	}, [products, searchTerm]);

	const fetchProducts = async () => {
		try {
			setLoading(true);
			setError("");
			const response = await apiService.getProducts();
			setProducts(response.products || []);
		} catch (err) {
			setError(err.message);
			console.error("Failed to fetch products:", err);
		} finally {
			setLoading(false);
		}
	};

	const handleSearch = (e) => {
		setSearchTerm(e.target.value);
	};

	const handleDelete = async (productId) => {
		if (window.confirm("Are you sure you want to delete this product?")) {
			try {
				await apiService.deleteProduct(productId);
				setProducts(products.filter(product => product.id !== productId));
			} catch (err) {
				setError(err.message);
			}
		}
	};

	if (loading) {
		return (
			<motion.div
				className='bg-gray-800 bg-opacity-50 backdrop-blur-md shadow-lg rounded-xl p-6 border border-gray-700 mb-8'
				initial={{ opacity: 0, y: 20 }}
				animate={{ opacity: 1, y: 0 }}
				transition={{ delay: 0.2 }}
			>
				<div className="flex items-center justify-center h-64">
					<div className="animate-spin rounded-full h-8 w-8 border-b-2 border-blue-500"></div>
					<span className="ml-3 text-gray-300">Loading products...</span>
				</div>
			</motion.div>
		);
	}

	return (
		<motion.div
			className='bg-gray-800 bg-opacity-50 backdrop-blur-md shadow-lg rounded-xl p-6 border border-gray-700 mb-8'
			initial={{ opacity: 0, y: 20 }}
			animate={{ opacity: 1, y: 0 }}
			transition={{ delay: 0.2 }}
		>
			<div className='flex justify-between items-center mb-6'>
				<h2 className='text-xl font-semibold text-gray-100'>Product List</h2>
				<div className='relative'>
					<input
						type='text'
						placeholder='Search products...'
						className='bg-gray-700 text-white placeholder-gray-400 rounded-lg pl-10 pr-4 py-2 focus:outline-none focus:ring-2 focus:ring-blue-500'
						onChange={handleSearch}
						value={searchTerm}
					/>
					<Search className='absolute left-3 top-2.5 text-gray-400' size={18} />
				</div>
			</div>

			{error && (
				<motion.div
					className="mb-4 p-4 bg-red-900 bg-opacity-50 border border-red-700 rounded-lg flex items-center"
					initial={{ opacity: 0 }}
					animate={{ opacity: 1 }}
				>
					<AlertCircle className="text-red-400 mr-3" size={20} />
					<span className="text-red-200 text-sm">{error}</span>
					<button
						onClick={fetchProducts}
						className="ml-auto text-red-200 underline hover:text-red-100"
					>
						Retry
					</button>
				</motion.div>
			)}

			<div className='overflow-x-auto'>
				<table className='min-w-full divide-y divide-gray-700'>
					<thead>
						<tr>
							<th className='px-6 py-3 text-left text-xs font-medium text-gray-400 uppercase tracking-wider'>
								Name
							</th>
							<th className='px-6 py-3 text-left text-xs font-medium text-gray-400 uppercase tracking-wider'>
								Category
							</th>
							<th className='px-6 py-3 text-left text-xs font-medium text-gray-400 uppercase tracking-wider'>
								Price
							</th>
							<th className='px-6 py-3 text-left text-xs font-medium text-gray-400 uppercase tracking-wider'>
								Stock
							</th>
							<th className='px-6 py-3 text-left text-xs font-medium text-gray-400 uppercase tracking-wider'>
								Sales
							</th>
							<th className='px-6 py-3 text-left text-xs font-medium text-gray-400 uppercase tracking-wider'>
								Actions
							</th>
						</tr>
					</thead>

					<tbody className='divide-y divide-gray-700'>
						{filteredProducts.length === 0 ? (
							<tr>
								<td colSpan="6" className="px-6 py-4 text-center text-gray-400">
									{products.length === 0 ? "No products found" : "No products match your search"}
								</td>
							</tr>
						) : (
							filteredProducts.map((product) => (
								<motion.tr
									key={product.id}
									initial={{ opacity: 0 }}
									animate={{ opacity: 1 }}
									transition={{ duration: 0.3 }}
								>
									<td className='px-6 py-4 whitespace-nowrap text-sm font-medium text-gray-100 flex gap-2 items-center'>
										<img
											src={product.imageUrl || 'https://images.unsplash.com/photo-1627989580309-bfaf3e58af6f?w=500&auto=format&fit=crop&q=60&ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxzZWFyY2h8Nnx8d2lyZWxlc3MlMjBlYXJidWRzfGVufDB8fDB8fHww'}
											alt={product.name}
											className='size-10 rounded-full object-cover'
											onError={(e) => {
												e.target.src = 'https://images.unsplash.com/photo-1627989580309-bfaf3e58af6f?w=500&auto=format&fit=crop&q=60&ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxzZWFyY2h8Nnx8d2lyZWxlc3MlMjBlYXJidWRzfGVufDB8fDB8fHww';
											}}
										/>
										{product.name}
									</td>

									<td className='px-6 py-4 whitespace-nowrap text-sm text-gray-300'>
										{product.category}
									</td>

									<td className='px-6 py-4 whitespace-nowrap text-sm text-gray-300'>
										${product.price.toFixed(2)}
									</td>
									<td className='px-6 py-4 whitespace-nowrap text-sm text-gray-300'>{product.stock}</td>
									<td className='px-6 py-4 whitespace-nowrap text-sm text-gray-300'>{product.sales || 0}</td>
									<td className='px-6 py-4 whitespace-nowrap text-sm text-gray-300'>
										<button className='text-indigo-400 hover:text-indigo-300 mr-2'>
											<Edit size={18} />
										</button>
										<button
											onClick={() => handleDelete(product.id)}
											className='text-red-400 hover:text-red-300'
										>
											<Trash2 size={18} />
										</button>
									</td>
								</motion.tr>
							))
						)}
					</tbody>
				</table>
			</div>
		</motion.div>
	);
};
export default ProductsTable;

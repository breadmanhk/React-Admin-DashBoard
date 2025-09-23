import { useState, useEffect } from "react";
import { motion } from "framer-motion";
import { Search, Edit, Trash2, AlertCircle } from "lucide-react";
import apiService from "../../services/api";

const UsersTable = () => {
	const [searchTerm, setSearchTerm] = useState("");
	const [users, setUsers] = useState([]);
	const [filteredUsers, setFilteredUsers] = useState([]);
	const [loading, setLoading] = useState(true);
	const [error, setError] = useState("");

	useEffect(() => {
		fetchUsers();
	}, []);

	useEffect(() => {
		const filtered = users.filter(
			(user) =>
				user.name.toLowerCase().includes(searchTerm.toLowerCase()) ||
				user.email.toLowerCase().includes(searchTerm.toLowerCase())
		);
		setFilteredUsers(filtered);
	}, [users, searchTerm]);

	const fetchUsers = async () => {
		try {
			setLoading(true);
			setError("");
			const response = await apiService.getUsers();
			setUsers(response.users || []);
		} catch (err) {
			setError(err.message);
			console.error("Failed to fetch users:", err);
		} finally {
			setLoading(false);
		}
	};

	const handleSearch = (e) => {
		setSearchTerm(e.target.value);
	};

	const handleDelete = async (userId) => {
		if (window.confirm("Are you sure you want to delete this user?")) {
			try {
				await apiService.deleteUser(userId);
				setUsers(users.filter(user => user.id !== userId));
			} catch (err) {
				setError(err.message);
			}
		}
	};

	if (loading) {
		return (
			<motion.div
				className='bg-gray-800 bg-opacity-50 backdrop-blur-md shadow-lg rounded-xl p-6 border border-gray-700'
				initial={{ opacity: 0, y: 20 }}
				animate={{ opacity: 1, y: 0 }}
				transition={{ delay: 0.2 }}
			>
				<div className="flex items-center justify-center h-64">
					<div className="animate-spin rounded-full h-8 w-8 border-b-2 border-blue-500"></div>
					<span className="ml-3 text-gray-300">Loading users...</span>
				</div>
			</motion.div>
		);
	}

	return (
		<motion.div
			className='bg-gray-800 bg-opacity-50 backdrop-blur-md shadow-lg rounded-xl p-6 border border-gray-700'
			initial={{ opacity: 0, y: 20 }}
			animate={{ opacity: 1, y: 0 }}
			transition={{ delay: 0.2 }}
		>
			<div className='flex justify-between items-center mb-6'>
				<h2 className='text-xl font-semibold text-gray-100'>Users</h2>
				<div className='relative'>
					<input
						type='text'
						placeholder='Search users...'
						className='bg-gray-700 text-white placeholder-gray-400 rounded-lg pl-10 pr-4 py-2 focus:outline-none focus:ring-2 focus:ring-blue-500'
						value={searchTerm}
						onChange={handleSearch}
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
						onClick={fetchUsers}
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
								Email
							</th>
							<th className='px-6 py-3 text-left text-xs font-medium text-gray-400 uppercase tracking-wider'>
								Role
							</th>
							<th className='px-6 py-3 text-left text-xs font-medium text-gray-400 uppercase tracking-wider'>
								Status
							</th>
							<th className='px-6 py-3 text-left text-xs font-medium text-gray-400 uppercase tracking-wider'>
								Actions
							</th>
						</tr>
					</thead>

					<tbody className='divide-y divide-gray-700'>
						{filteredUsers.length === 0 ? (
							<tr>
								<td colSpan="5" className="px-6 py-4 text-center text-gray-400">
									{users.length === 0 ? "No users found" : "No users match your search"}
								</td>
							</tr>
						) : (
							filteredUsers.map((user) => (
								<motion.tr
									key={user.id}
									initial={{ opacity: 0 }}
									animate={{ opacity: 1 }}
									transition={{ duration: 0.3 }}
								>
									<td className='px-6 py-4 whitespace-nowrap'>
										<div className='flex items-center'>
											<div className='flex-shrink-0 h-10 w-10'>
												<div className='h-10 w-10 rounded-full bg-gradient-to-r from-purple-400 to-blue-500 flex items-center justify-center text-white font-semibold'>
													{user.name.charAt(0)}
												</div>
											</div>
											<div className='ml-4'>
												<div className='text-sm font-medium text-gray-100'>{user.name}</div>
											</div>
										</div>
									</td>

									<td className='px-6 py-4 whitespace-nowrap'>
										<div className='text-sm text-gray-300'>{user.email}</div>
									</td>
									<td className='px-6 py-4 whitespace-nowrap'>
										<span className='px-2 inline-flex text-xs leading-5 font-semibold rounded-full bg-blue-800 text-blue-100'>
											{user.role}
										</span>
									</td>

									<td className='px-6 py-4 whitespace-nowrap'>
										<span
											className={`px-2 inline-flex text-xs leading-5 font-semibold rounded-full ${
												user.status === "ACTIVE"
													? "bg-green-800 text-green-100"
													: "bg-red-800 text-red-100"
											}`}
										>
											{user.status}
										</span>
									</td>

									<td className='px-6 py-4 whitespace-nowrap text-sm text-gray-300'>
										<button className='text-indigo-400 hover:text-indigo-300 mr-2'>
											<Edit size={18} />
										</button>
										<button
											onClick={() => handleDelete(user.id)}
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
export default UsersTable;

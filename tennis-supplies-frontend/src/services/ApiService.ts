interface User {
  id: number;
  name: string;

}

const BASE_URL = `http://${window.location.hostname}:8082`;


export async function getAllLanes(startTimeInUnix: bigint): Promise<Response> {
  try {
    const response = await fetch(`${BASE_URL}/allLanes`);
    if (!response.ok) {
      console.error('Error fetching lanes');
    }
    return await response.json();
  } catch (error) {
    console.error('Error getting lanes:', error);
    throw error;
  }
}

async function getLane(laneNo: number): Promise<Response> {
  try {
    const response = await fetch(`${BASE_URL}/lane/${laneNo}`);
    if (!response.ok) {
      console.error(`Error fetching lane ${laneNo}`);
    }
    return await response.json();
  } catch (error) {
    console.error('Error getting lanes:', error);
    throw error;
  }
}

async function getAllUsers(): Promise<Response> {
  try {
    const response = await fetch(`${BASE_URL}/allUsers`);
    if (!response.ok) {
      console.error('Error fetching users');
    }
    return await response.json();
  } catch (error) {
    console.error('Error getting users:', error);
    throw error;
  }
}

async function rentLane(userList: User[], laneNo: number, startTimeInUnix: bigint): Promise<Response> {
  try {
    const requestBody  = {
      userList: userList,
      laneNo: laneNo,
      startTimeInUnix: startTimeInUnix
    };
    const response = await fetch(`${BASE_URL}/rentLane`, {
      method: 'POST',
      headers: {
        'Content-Type': 'application/json',
      },
      body: JSON.stringify(requestBody)
    });
    if (!response.ok) {
      console.error('Error renting lane');
    }
    return response;
  } catch (error) {
    console.error('Error renting lane:', error);
    throw error;
  }
}
